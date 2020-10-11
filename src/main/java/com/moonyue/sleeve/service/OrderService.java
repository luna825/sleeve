package com.moonyue.sleeve.service;

import com.moonyue.sleeve.common.util.OrderUtil;
import com.moonyue.sleeve.core.bean.OrderStatus;
import com.moonyue.sleeve.core.exception.NotFoundException;
import com.moonyue.sleeve.core.exception.ParameterException;
import com.moonyue.sleeve.core.money.IMoneyDiscount;
import com.moonyue.sleeve.dto.OrderDTO;
import com.moonyue.sleeve.dto.SkuInfoDTO;
import com.moonyue.sleeve.logic.CouponChecker;
import com.moonyue.sleeve.logic.OrderChecker;
import com.moonyue.sleeve.model.*;
import com.moonyue.sleeve.repository.CouponRepository;
import com.moonyue.sleeve.repository.OrderRepository;
import com.moonyue.sleeve.repository.SkuRepository;
import com.moonyue.sleeve.repository.UserCouponRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {

    @Autowired
    private SkuRepository skuRepository;

    @Autowired
    private CouponRepository couponRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserCouponRepository userCouponRepository;

    @Autowired
    private IMoneyDiscount iMoneyDiscount;


    @Transactional
    public Long placeOrder(Long uid, OrderDTO orderDTO, OrderChecker orderChecker){

        Order order = Order.builder()
                .orderNo(OrderUtil.makeOrderNo())
                .userId(uid)
                .finalTotalPrice(orderDTO.getFinalTotalPrice())
                .snapImg(orderChecker.getLeaderImg())
                .snapTitle(orderChecker.getLeaderTitle())
                .status(OrderStatus.UNPAID.value())
                .totalCount(orderChecker.getTotalCount())
                .totalPrice(orderDTO.getTotalPrice())
                .build();
        order.setSnapItems(orderChecker.getOrderSkuList());
        order.setSnapAddress(orderDTO.getAddress());
        this.orderRepository.save(order);
        //reduceStock 减库存
        this.reduceStock(orderChecker);
        // 核销优惠券
        if(orderDTO.getCouponId() != null){
            this.writeOffCoupon(uid, orderDTO.getCouponId(), order.getId());
        }
        // 加入延时队列
        return order.getId();
    }

    private void reduceStock(OrderChecker orderChecker){
        List<OrderSku> orderSkuList = orderChecker.getOrderSkuList();
        for(OrderSku orderSku: orderSkuList){
            int result = this.skuRepository.reduceStock(orderSku.getId(), orderSku.getCount().longValue());
            if( result != 1){
                throw new ParameterException(50003);
            }
        }
    }

    private void writeOffCoupon(Long uid, Long couponId, Long orderId){
        int result = this.userCouponRepository.writeOff(uid,couponId,orderId);
        if(result != 1){
            throw new ParameterException(40012);
        }

    }


    public OrderChecker isOk(Long uid, OrderDTO orderDTO){
        if(orderDTO.getFinalTotalPrice().compareTo(new BigDecimal("0")) <= 0){
            throw new ParameterException(50011);
        }

        List<Long> skuIdList = orderDTO.getSkuInfoList().stream()
                .map(SkuInfoDTO::getId)
                .collect(Collectors.toList());

        List<Sku> skuList = this.skuRepository.findAllByIdIn(skuIdList);

        Long couponId = orderDTO.getCouponId();
        CouponChecker couponChecker = null;
        if(couponId != null){
            Coupon coupon = this.couponRepository.findById(couponId)
                    .orElseThrow(()->new NotFoundException(40004));
            UserCoupon userCoupon = this.userCouponRepository.findFirstByUserIdAndCouponId(uid, couponId)
                    .orElseThrow(()->new NotFoundException(50006));

            couponChecker = new CouponChecker(coupon, iMoneyDiscount);
        }

        OrderChecker orderChecker = new OrderChecker(orderDTO, skuList, couponChecker);
        orderChecker.isOk();
        return orderChecker;
    }
}

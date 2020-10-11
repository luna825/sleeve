package com.moonyue.sleeve.logic;

import com.moonyue.sleeve.bo.SkuOrderBO;
import com.moonyue.sleeve.core.exception.ParameterException;
import com.moonyue.sleeve.dto.OrderDTO;
import com.moonyue.sleeve.dto.SkuInfoDTO;
import com.moonyue.sleeve.model.OrderSku;
import com.moonyue.sleeve.model.Sku;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class OrderChecker {

    private final OrderDTO orderDTO;
    private final List<Sku> serverSkuList;
    private final CouponChecker couponChecker;

    @Getter
    private final List<OrderSku> orderSkuList = new ArrayList<>();


    public OrderChecker(OrderDTO orderDTO, List<Sku> serverSkuList, CouponChecker couponChecker) {
        this.orderDTO = orderDTO;
        this.serverSkuList = serverSkuList;
        this.couponChecker = couponChecker;
    }

    public String getLeaderImg() {
        return  this.serverSkuList.get(0).getImg();
    }

    public String getLeaderTitle() {
        return this.serverSkuList.get(0).getTitle();
    }

    public Integer getTotalCount() {
        return this.orderDTO.getSkuInfoList()
                .stream()
                .map(SkuInfoDTO::getCount)
                .reduce(Integer::sum)
                .orElse(0);
    }

    public void isOk(){

        this.skuNotOnSale(serverSkuList.size(), orderDTO.getSkuInfoList().size());

        BigDecimal serverTotalPrice = new BigDecimal("0");
        List<SkuOrderBO> skuOrderBOList = new ArrayList<>();

        for (int i = 0; i < serverSkuList.size(); i++) {
            Sku sku = serverSkuList.get(i);
            SkuInfoDTO skuInfoDTO = orderDTO.getSkuInfoList().get(i);
            this.containsSoldOutSku(sku);
            this.beyondSkuStock(sku, skuInfoDTO);

            serverTotalPrice = serverTotalPrice.add(calculateSkuOrderPrice(sku, skuInfoDTO));
            skuOrderBOList.add(new SkuOrderBO(sku, skuInfoDTO));
            this.orderSkuList.add(new OrderSku(sku, skuInfoDTO));
        }


        this.totalPriceIsOk(orderDTO.getTotalPrice(), serverTotalPrice);

        if(couponChecker != null){
            couponChecker.isOk();
            couponChecker.canBeUsed(skuOrderBOList, serverTotalPrice);
            couponChecker.finalTotalPriceIsOk(orderDTO.getFinalTotalPrice(), serverTotalPrice);
        }
    }

    //校验总价格
    private void totalPriceIsOk(BigDecimal orderTotalPrice, BigDecimal serverTotalPrice){
        if(orderTotalPrice.compareTo(serverTotalPrice) != 0){
            throw new ParameterException(50005);
        }
    }

    //计算种sku的价格
    private BigDecimal calculateSkuOrderPrice(Sku sku, SkuInfoDTO skuInfoDTO){
        if (skuInfoDTO.getCount() <= 0) {
            throw new ParameterException(50007);
        }
        return sku.getActualPrice().multiply(new BigDecimal(skuInfoDTO.getCount()));
    }

    // 是否有商品下架
    private void skuNotOnSale(int count1, int count2){
        if(count1 != count2){
            throw new ParameterException(50002);
        }
    }

    // 没有库存
    private void containsSoldOutSku(Sku sku){
        if(sku.getStock() == 0){
            throw new ParameterException(50001);
        }
    }

    // 库存不足
    private void beyondSkuStock(Sku sku, SkuInfoDTO skuInfoDTO){
        if (sku.getStock() < skuInfoDTO.getCount()){
            throw new ParameterException(50003);
        }
    }
}

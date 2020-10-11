package com.moonyue.sleeve.logic;

import com.moonyue.sleeve.bo.SkuOrderBO;
import com.moonyue.sleeve.common.util.CommonUtil;
import com.moonyue.sleeve.core.bean.CouponType;
import com.moonyue.sleeve.core.exception.ForbiddenException;
import com.moonyue.sleeve.core.exception.ParameterException;
import com.moonyue.sleeve.core.money.IMoneyDiscount;
import com.moonyue.sleeve.model.Category;
import com.moonyue.sleeve.model.Coupon;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class CouponChecker {

    private Coupon coupon;
    private IMoneyDiscount iMoneyDiscount;

    public CouponChecker(Coupon coupon, IMoneyDiscount iMoneyDiscount){
        this.coupon = coupon;
        this.iMoneyDiscount = iMoneyDiscount;
    }

    public void isOk(){
        Date now = new Date();
        Boolean isIn = CommonUtil.isInTimeLine(now, coupon.getStartTime(), coupon.getEndTime());
        if(!isIn){
            throw new ForbiddenException(40007);
        }

    }

    public void finalTotalPriceIsOk(BigDecimal orderFinalTotalPrice, BigDecimal serverTotalPrice){

        BigDecimal serverFinalTotalPrice;

        switch (CouponType.toType(coupon.getType())){
            case FULL_MINUS:
            case NO_THRESHOLD_MINUS:
                serverFinalTotalPrice = serverTotalPrice.subtract(this.coupon.getMinus());
                break;
            case FULL_OFF:
                serverFinalTotalPrice = iMoneyDiscount.discount(serverTotalPrice, this.coupon.getRate());
                break;
            default:
                throw new ForbiddenException(40009);

        }
        if(serverFinalTotalPrice.compareTo(orderFinalTotalPrice) != 0){
            throw new ForbiddenException(50008);
        }
    }

    public void canBeUsed(List<SkuOrderBO> skuOrderBOList, BigDecimal serverTotalPrice){
        BigDecimal orderCategoryPrice;

        if(this.coupon.getWholeStore()){
            orderCategoryPrice = serverTotalPrice;
        }else{
            List<Long> cidList = coupon.getCategoryList().stream()
                    .map(Category::getId)
                    .collect(Collectors.toList());
            orderCategoryPrice = this.getSumByCategoryList(skuOrderBOList, cidList);
        }

        this.couponCanBeUsed(orderCategoryPrice);

    }

    private void couponCanBeUsed(BigDecimal orderCategoryPrice){
        switch (CouponType.toType(coupon.getType())){
            case FULL_OFF:
            case FULL_MINUS:
                int compare = coupon.getFullMoney().compareTo(orderCategoryPrice);
                if(compare > 0){
                    throw new ParameterException(40008);
                }
                break;
            case NO_THRESHOLD_MINUS:
                break;
            default:
                throw new ParameterException(40009);
        }
    }

    private BigDecimal getSumByCategoryList(List<SkuOrderBO> skuOrderBOList, List<Long> cidList){
        return cidList.stream()
                .map(cid->this.getSumByCategory(skuOrderBOList, cid))
                .reduce(BigDecimal::add)
                .orElse(new BigDecimal("0"));
    }

    private BigDecimal getSumByCategory(List<SkuOrderBO> skuOrderBOList, Long cid){
        return skuOrderBOList.stream()
                .filter(s -> s.getCategoryId().equals(cid))
                .map(SkuOrderBO::getTotalPrice)
                .reduce(BigDecimal::add)
                .orElse(new BigDecimal("0"));
    }
}

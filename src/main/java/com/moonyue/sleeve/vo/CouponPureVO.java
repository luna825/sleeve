package com.moonyue.sleeve.vo;

import com.moonyue.sleeve.model.Coupon;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Setter
@Getter
public class CouponPureVO {

    private Long id;
    private String title;
    private Date startTime;
    private Date endTime;
    private String description;
    private BigDecimal fullMoney;
    private BigDecimal minus;
    private BigDecimal rate;
    private Integer type;
    private String remark;
    private Boolean wholeStore;

    public CouponPureVO(Coupon coupon){
        BeanUtils.copyProperties(coupon, this);
    }

    public static List<CouponPureVO> getList(List<Coupon> coupons){
        if(coupons == null){
            return Collections.emptyList();
        }

        return coupons.stream().map(CouponPureVO::new).collect(Collectors.toList());
    }
}

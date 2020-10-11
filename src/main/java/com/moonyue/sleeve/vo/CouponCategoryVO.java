package com.moonyue.sleeve.vo;

import com.moonyue.sleeve.model.Coupon;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@Setter
@Getter
public class CouponCategoryVO extends CouponPureVO {
    List<CategoryPureVO> categories;

    public CouponCategoryVO(Coupon coupon){
        super(coupon);
        this.categories = coupon.getCategoryList().stream()
                .map(CategoryPureVO::new).collect(Collectors.toList());
    }
}

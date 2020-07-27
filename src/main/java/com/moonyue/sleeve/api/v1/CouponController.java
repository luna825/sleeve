package com.moonyue.sleeve.api.v1;


import com.moonyue.sleeve.model.Coupon;
import com.moonyue.sleeve.service.CouponService;
import com.moonyue.sleeve.vo.CouponPureVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Positive;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/v1/coupon")
@Validated
public class CouponController {

    @Autowired
    private CouponService couponService;

    @GetMapping("by/category/{cid}")
    public List<CouponPureVO> getByCategory(@PathVariable @Positive Long cid){
        List<Coupon> coupons = this.couponService.getCouponByCategory(cid);
        return coupons.stream()
                .map(CouponPureVO::new)
                .collect(Collectors.toList());
    }

    @GetMapping("whole_store")
    public List<CouponPureVO> getWholeStore(){
        List<Coupon> coupons = this.couponService.getWholeStoreCoupon();
        return coupons.stream()
                .map(CouponPureVO::new)
                .collect(Collectors.toList());
    }
}

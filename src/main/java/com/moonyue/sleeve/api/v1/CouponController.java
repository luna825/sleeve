package com.moonyue.sleeve.api.v1;


import com.moonyue.sleeve.common.util.CommonUtil;
import com.moonyue.sleeve.core.LocalUser;
import com.moonyue.sleeve.core.annotations.ScopeLevel;
import com.moonyue.sleeve.core.bean.CouponStatus;
import com.moonyue.sleeve.core.exception.ParameterException;
import com.moonyue.sleeve.model.Coupon;
import com.moonyue.sleeve.model.User;
import com.moonyue.sleeve.service.CouponService;
import com.moonyue.sleeve.vo.CouponCategoryVO;
import com.moonyue.sleeve.vo.CouponPureVO;
import com.moonyue.sleeve.vo.CreatedVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Positive;
import java.util.Collections;
import java.util.Date;
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

    @PostMapping("collect/{id}")
    @ScopeLevel()
    public CreatedVO collect(@PathVariable(name = "id") @Positive Long couponId){
        Long uid = LocalUser.getUser().getId();
        this.couponService.collectOneCoupon(uid, couponId);
        return new CreatedVO("领取优惠券成功");
    }

    @GetMapping("/myself/by/status/{status}")
    @ScopeLevel
    public List<CouponPureVO> getMyCouponByStatus(@PathVariable @Positive Integer status){
        Long uid = LocalUser.getUser().getId();
        List<Coupon> coupons;

        switch (CouponStatus.toType(status)){
            case AVAILABLE:
                coupons = this.couponService.getMyAvailableCoupons(uid);
                break;
            case USED:
                coupons = this.couponService.getMyUsedCoupons(uid);
                break;
            case EXPIRED:
                coupons = this.couponService.getMyExpiredCoupons(uid);
                break;
            default:
                throw new ParameterException(40001);
        }

        return CouponPureVO.getList(coupons);
    }

    @ScopeLevel()
    @GetMapping("/myself/available/with_category")
    public List<CouponCategoryVO> getUserCouponWithCategory() {
        User user = LocalUser.getUser();
        List<Coupon> coupons = couponService.getMyAvailableCoupons(user.getId());
        if (coupons.isEmpty()) {
            return Collections.emptyList();
        }
        Date now = new Date();
        return coupons.stream()
                .filter(c-> CommonUtil.isInTimeLine(now, c.getStartTime(), c.getEndTime())) // 过滤掉未到开始使用时间的优惠券
                .map(CouponCategoryVO::new).collect(Collectors.toList());
    }

}

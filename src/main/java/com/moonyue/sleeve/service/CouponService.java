package com.moonyue.sleeve.service;

import com.moonyue.sleeve.common.util.CommonUtil;
import com.moonyue.sleeve.core.bean.CouponStatus;
import com.moonyue.sleeve.core.exception.NotFoundException;
import com.moonyue.sleeve.core.exception.ParameterException;
import com.moonyue.sleeve.model.Activity;
import com.moonyue.sleeve.model.Coupon;
import com.moonyue.sleeve.model.UserCoupon;
import com.moonyue.sleeve.repository.ActivityRepository;
import com.moonyue.sleeve.repository.CouponRepository;
import com.moonyue.sleeve.repository.UserCouponRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class CouponService {

    @Autowired
    private CouponRepository couponRepository;

    @Autowired
    private ActivityRepository activityRepository;

    @Autowired
    private UserCouponRepository userCouponRepository;

    public List<Coupon> getCouponByCategory(Long cid){
        Date now = new Date();
        return this.couponRepository.findByCategory(cid, now);
    }

    public List<Coupon> getWholeStoreCoupon(){
        Date now = new Date();
        return this.couponRepository.findByWholeStore(true, now);
    }

    public List<Coupon> getMyAvailableCoupons(Long uid){
        Date now = new Date();
        return this.couponRepository.findMyAvailable(uid, now);
    }

    public List<Coupon> getMyUsedCoupons(Long uid){
        Date now = new Date();
        return this.couponRepository.findMyUsed(uid, now);
    }

    public List<Coupon> getMyExpiredCoupons(Long uid){
        Date now = new Date();
        return this.couponRepository.findMyExpired(uid, now);
    }

    public void collectOneCoupon(Long uid, Long couponId){
        this.couponRepository.findById(couponId).orElseThrow(()->new NotFoundException(40004));
        Activity activity = this.activityRepository.findByCouponListId(couponId)
                .orElseThrow(() -> new NotFoundException(40010));
        Date now = new Date();
        Boolean isIn = CommonUtil.isInTimeLine(now, activity.getStartTime(), activity.getEndTime());
        if(!isIn){
            throw new ParameterException(40004);
        }

        this.userCouponRepository.findFirstByUserIdAndCouponId(uid, couponId)
                .ifPresent((uc) -> { throw new ParameterException(40006);});

        UserCoupon userCoupon = UserCoupon.builder()
                .userId(uid)
                .couponId(couponId)
                .createTime(now)
                .status(CouponStatus.AVAILABLE.getValue())
                .build();
        this.userCouponRepository.save(userCoupon);
    }

}

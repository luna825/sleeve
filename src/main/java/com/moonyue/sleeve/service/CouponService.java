package com.moonyue.sleeve.service;

import com.moonyue.sleeve.model.Coupon;
import com.moonyue.sleeve.repository.CouponRepository;
import com.moonyue.sleeve.vo.CouponPureVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CouponService {

    @Autowired
    private CouponRepository couponRepository;

    public List<Coupon> getCouponByCategory(Long cid){
        Date now = new Date();
        return this.couponRepository.findByCategory(cid, now);
    }

    public List<Coupon> getWholeStoreCoupon(){
        Date now = new Date();
        return this.couponRepository.findByWholeStore(true, now);
    }
}

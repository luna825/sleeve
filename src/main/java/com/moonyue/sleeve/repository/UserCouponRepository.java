package com.moonyue.sleeve.repository;


import com.moonyue.sleeve.model.UserCoupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserCouponRepository extends JpaRepository<UserCoupon, Long> {

    Optional<UserCoupon> findFirstByUserIdAndCouponId(Long uid, Long couponId);

    @Modifying
    @Query("update UserCoupon uc set uc.status = 2, uc.orderId = :oid\n" +
            "where uc.userId = :uid and uc.couponId = :cid\n" +
            "and uc.status = 1 and uc.orderId is null")
    int writeOff(Long uid, Long cid, Long oid);
}

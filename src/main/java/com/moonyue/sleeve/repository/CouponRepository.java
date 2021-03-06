package com.moonyue.sleeve.repository;

import com.moonyue.sleeve.model.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

public interface CouponRepository extends JpaRepository<Coupon, Long> {

    @Query("select c from Coupon c\n" +
            "join c.categoryList ca\n" +
            "join Activity a on a.id = c.activityId\n" +
            "where ca.id =:cid and :now > a.startTime and :now < a.endTime")
    List<Coupon> findByCategory(Long cid, Date now);


    @Query("select c from Coupon c\n" +
            "join Activity a on c.activityId = a.id\n" +
            "where c.wholeStore = :isWholeStore\n" +
            "and c.startTime < :now\n" +
            "and c.endTime > :now")
    List<Coupon> findByWholeStore(Boolean isWholeStore, Date now);

    @Query("select c from Coupon c\n" +
            "join UserCoupon uc on c.id = uc.couponId\n" +
            "join User u on uc.userId = u.id\n" +
            "where u.id = :uid\n" +
            "and uc.status = 1\n" +
            "and uc.orderId is null\n" +
            "and c.endTime > :now")
    List<Coupon> findMyAvailable(Long uid, Date now);

    @Query("select c from Coupon c\n" +
            "join UserCoupon uc on c.id = uc.couponId\n" +
            "join User u on uc.userId = u.id\n" +
            "where u.id = :uid\n" +
            "and uc.orderId is not null\n" +
            "and uc.status = 2\n" +
            "and c.startTime < :now\n" +
            "and c.endTime > :now")
    List<Coupon> findMyUsed(Long uid, Date now);

    @Query("select c from Coupon c\n" +
            "join UserCoupon uc on c.id = uc.couponId\n" +
            "join User u on uc.userId = u.id\n" +
            "where u.id = :uid\n" +
            "and uc.orderId is null\n" +
            "and uc.status <> 2\n" +
            "and c.endTime < :now")
    List<Coupon> findMyExpired(Long uid, Date now);

}

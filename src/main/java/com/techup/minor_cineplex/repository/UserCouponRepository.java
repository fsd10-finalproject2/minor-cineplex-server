package com.techup.minor_cineplex.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.techup.minor_cineplex.entity.UserCoupon;
import com.techup.minor_cineplex.entity.UserCoupon.Status;

public interface UserCouponRepository extends JpaRepository<UserCoupon, Long> {

    Optional<UserCoupon> findByUserIdAndCoupon_Id(UUID userId, Integer couponId);

    @EntityGraph(attributePaths = {"coupon", "coupon.couponInfo", "coupon.couponPartner"})
    List<UserCoupon> findAllByUserIdOrderBySavedAtDesc(UUID userId);

    @EntityGraph(attributePaths = {"coupon", "coupon.couponInfo", "coupon.couponPartner"})
    List<UserCoupon> findAllByUserIdAndStatusOrderBySavedAtDesc(UUID userId, Status status);

    @Query("select distinct uc.coupon.id from UserCoupon uc where uc.userId = :userId")
    List<Integer> findDistinctCouponIdsByUserId(@Param("userId") UUID userId);
}

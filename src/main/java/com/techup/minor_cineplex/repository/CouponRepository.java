package com.techup.minor_cineplex.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.techup.minor_cineplex.entity.Coupons;

public interface CouponRepository extends JpaRepository<Coupons, Integer> {

    @Query("SELECT DISTINCT c FROM Coupons c "
        + "LEFT JOIN FETCH c.couponInfo "
        + "LEFT JOIN FETCH c.couponPartner")
    List<Coupons> findAllWithCouponInfo();

    Optional<Coupons> findByCode(String code);
}

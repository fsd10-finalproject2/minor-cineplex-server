package com.techup.minor_cineplex.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.techup.minor_cineplex.entity.Coupons;

public interface CouponRepository extends JpaRepository<Coupons, Integer> {

    @Query("SELECT DISTINCT c FROM Coupons c "
        + "LEFT JOIN FETCH c.couponInfo "
        + "LEFT JOIN FETCH c.couponPartner")
    List<Coupons> findAllWithCouponInfo();

    @EntityGraph(attributePaths = {"couponInfo", "couponPartner"})
    Page<Coupons> findAllByIsActiveTrue(Pageable pageable);

    @EntityGraph(attributePaths = {"couponInfo", "couponPartner"})
    Page<Coupons> findAllByIsActiveTrueAndCouponPartner_PartnerNameIgnoreCase(String partnerName, Pageable pageable);

    Optional<Coupons> findByCode(String code);
}

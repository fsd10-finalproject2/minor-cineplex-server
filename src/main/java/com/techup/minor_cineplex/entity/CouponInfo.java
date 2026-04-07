package com.techup.minor_cineplex.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "coupon_info")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CouponInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Integer id;

    @Column(name = "coupon_name")
    private String couponName;

    @Column(name = "coupon_description")
    private String couponDescription;

    @Column(name = "coupon_terms_conditions")
    private String couponTermsConditions;
}

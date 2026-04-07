package com.techup.minor_cineplex.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "coupons")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Coupons {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Integer id;

    @Column(name = "code", nullable = false)
    private String code;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "coupon_info_id")
    private CouponInfo couponInfo;

    /** PostgreSQL enum; values must match labels in the DB type. */
    @Column(name = "discount_type", nullable = false)
    private String discountType;

    @Column(name = "discount_value", nullable = false)
    private BigDecimal discountValue;

    @Column(name = "max_discount")
    private BigDecimal maxDiscount;

    @Column(name = "min_price")
    private BigDecimal minPrice;

    @Column(name = "start_date")
    private LocalDateTime startDate;

    @Column(name = "expires_at")
    private LocalDateTime expiresAt;

    @Column(name = "usage_limit", nullable = false)
    private Integer usageLimit;

    @Column(name = "usage_per_user")
    private Integer usagePerUser;

    @Column(name = "coupons_img_url")
    private String couponsImgUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "coupon_partner_id")
    private CouponPartner couponPartner;

    @Column(name = "is_active")
    private Boolean isActive;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "redemption_start_at")
    private LocalDateTime redemptionStartAt;

    @Column(name = "redemption_expires_at")
    private LocalDateTime redemptionExpiresAt;

    @Column(name = "usage_count", nullable = false)
    private BigDecimal usageCount;

    /** PostgreSQL enum; values must match labels in the DB type. */
    @Column(name = "coupon_type", nullable = false)
    private String couponType;
}

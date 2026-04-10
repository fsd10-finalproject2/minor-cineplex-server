package com.techup.minor_cineplex.entity;

import java.time.LocalDateTime;

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
@Table(name = "coupon_partner")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CouponPartner {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Integer id;

    @Column(name = "partner_name", nullable = false)
    private String partnerName;

    /** PostgreSQL enum; values must match labels in the DB type. */
    @Column(name = "partner_type")
    private String partnerType;

    @Column(name = "created_at")
    private LocalDateTime createdAt;
}

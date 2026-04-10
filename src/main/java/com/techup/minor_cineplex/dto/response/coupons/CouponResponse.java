package com.techup.minor_cineplex.dto.response.coupons;

import java.time.LocalDateTime;

import com.techup.minor_cineplex.entity.CouponInfo;
import com.techup.minor_cineplex.entity.CouponPartner;
import com.techup.minor_cineplex.entity.Coupons;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CouponResponse {
    private final Integer id;
    private final String code;
    private final String couponName;
    private final String couponDescription;
    private final String couponTermsConditions;
    private final LocalDateTime startDate;
    private final LocalDateTime expiresAt;
    private final String couponsImgUrl;
    private final String partnerName;
    private final Boolean isActive;
    private final LocalDateTime redemptionStartAt;
    private final LocalDateTime redemptionExpiresAt;
    private final Boolean hasCoupon;

    public static CouponResponse from(Coupons entity) {
        return from(entity, false);
    }

    public static CouponResponse from(Coupons entity, boolean hasCoupon) {
        if (entity == null) {
            return null;
        }
        CouponInfo info = entity.getCouponInfo();
        CouponPartner partner = entity.getCouponPartner();
        return CouponResponse.builder()
            .id(entity.getId())
            .code(entity.getCode())
            .couponName(info != null ? info.getCouponName() : null)
            .couponDescription(info != null ? info.getCouponDescription() : null)
            .couponTermsConditions(info != null ? info.getCouponTermsConditions() : null)
            .startDate(entity.getStartDate())
            .expiresAt(entity.getExpiresAt())
            .couponsImgUrl(entity.getCouponsImgUrl())
            .partnerName(partner != null ? partner.getPartnerName() : null)
            .isActive(entity.getIsActive())
            .redemptionStartAt(entity.getRedemptionStartAt())
            .redemptionExpiresAt(entity.getRedemptionExpiresAt())
            .hasCoupon(hasCoupon)
            .build();
    }
}

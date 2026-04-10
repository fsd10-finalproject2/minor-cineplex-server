package com.techup.minor_cineplex.dto.response.coupons;

import java.time.LocalDateTime;

import com.techup.minor_cineplex.entity.UserCoupon;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MyCouponResponse {
    private final Integer couponId;
    private final String code;
    private final String couponName;
    private final String couponDescription;
    private final String couponTermsConditions;
    private final String couponsImgUrl;
    private final String partnerName;
    private final LocalDateTime expiresAt;
    private final String status;
    private final LocalDateTime savedAt;
    private final LocalDateTime usedAt;

    public static MyCouponResponse from(UserCoupon entity) {
        if (entity == null || entity.getCoupon() == null) {
            return null;
        }

        var coupon = entity.getCoupon();
        var info = coupon.getCouponInfo();
        var partner = coupon.getCouponPartner();

        return MyCouponResponse.builder()
            .couponId(coupon.getId())
            .code(coupon.getCode())
            .couponName(info != null ? info.getCouponName() : null)
            .couponDescription(info != null ? info.getCouponDescription() : null)
            .couponTermsConditions(info != null ? info.getCouponTermsConditions() : null)
            .couponsImgUrl(coupon.getCouponsImgUrl())
            .partnerName(partner != null ? partner.getPartnerName() : null)
            .expiresAt(coupon.getExpiresAt())
            .status(entity.getStatus() != null ? entity.getStatus().name() : null)
            .savedAt(entity.getSavedAt())
            .usedAt(entity.getUsedAt())
            .build();
    }
}

package com.techup.minor_cineplex.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.techup.minor_cineplex.dto.response.coupons.MyCouponResponse;
import com.techup.minor_cineplex.entity.Coupons;
import com.techup.minor_cineplex.entity.UserCoupon;
import com.techup.minor_cineplex.entity.UserCoupon.Status;
import com.techup.minor_cineplex.exception.AppException;
import com.techup.minor_cineplex.exception.ErrorCode;
import com.techup.minor_cineplex.repository.CouponRepository;
import com.techup.minor_cineplex.repository.UserCouponRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserCouponService {

    private final UserCouponRepository userCouponRepository;
    private final CouponRepository couponRepository;

    @Transactional
    public void saveCoupon(UUID userId, Integer couponId) {
        UserCoupon existing = userCouponRepository.findByUserIdAndCoupon_Id(userId, couponId).orElse(null);
        if (existing != null) {
            throw new AppException(ErrorCode.COUPON_ALREADY_SAVED);
        }

        Coupons coupon = couponRepository.findById(couponId)
            .orElseThrow(() -> new AppException(ErrorCode.COUPON_NOT_FOUND));

        if (coupon.getIsActive() == null || !coupon.getIsActive()) {
            throw new AppException(ErrorCode.COUPON_NOT_ACTIVE);
        }

        UserCoupon userCoupon = UserCoupon.builder()
            .userId(userId)
            .coupon(coupon)
            .status(Status.SAVED)
            .savedAt(LocalDateTime.now())
            .build();
        userCouponRepository.save(userCoupon);
    }

    @Transactional(readOnly = true)
    public List<MyCouponResponse> listMyCoupons(UUID userId, Status status) {
        List<UserCoupon> data = status == null
            ? userCouponRepository.findAllByUserIdOrderBySavedAtDesc(userId)
            : userCouponRepository.findAllByUserIdAndStatusOrderBySavedAtDesc(userId, status);

        return data.stream().map(MyCouponResponse::from).toList();
    }

    @Transactional
    public void removeCoupon(UUID userId, Integer couponId) {
        UserCoupon userCoupon = userCouponRepository.findByUserIdAndCoupon_Id(userId, couponId)
            .orElseThrow(() -> new AppException(ErrorCode.COUPON_NOT_IN_WALLET));

        userCoupon.setStatus(Status.REMOVED);
        userCouponRepository.save(userCoupon);
    }
}

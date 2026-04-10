package com.techup.minor_cineplex.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.techup.minor_cineplex.dto.response.coupons.MyCouponResponse;
import com.techup.minor_cineplex.entity.UserCoupon.Status;
import com.techup.minor_cineplex.service.UserCouponService;
import com.techup.minor_cineplex.utils.JwtUtils;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/user/me/coupons")
@RequiredArgsConstructor
public class UserCouponsController {

    private final UserCouponService userCouponService;

    @GetMapping("")
    public ResponseEntity<List<MyCouponResponse>> listMyCoupons(
        @AuthenticationPrincipal Jwt jwt,
        @RequestParam(required = false) Status status
    ) {
        return ResponseEntity.ok(userCouponService.listMyCoupons(JwtUtils.extractUserId(jwt), status));
    }

    @PostMapping("/{couponId}")
    public ResponseEntity<Map<String, String>> saveCoupon(
        @AuthenticationPrincipal Jwt jwt,
        @PathVariable Integer couponId
    ) {
        userCouponService.saveCoupon(JwtUtils.extractUserId(jwt), couponId);
        return ResponseEntity.ok(Map.of("message", "Coupon saved successfully"));
    }

    @DeleteMapping("/{couponId}")
    public ResponseEntity<Map<String, String>> removeCoupon(
        @AuthenticationPrincipal Jwt jwt,
        @PathVariable Integer couponId
    ) {
        userCouponService.removeCoupon(JwtUtils.extractUserId(jwt), couponId);
        return ResponseEntity.ok(Map.of("message", "Coupon removed from wallet"));
    }
}

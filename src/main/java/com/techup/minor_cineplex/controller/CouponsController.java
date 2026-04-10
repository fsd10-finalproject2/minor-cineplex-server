package com.techup.minor_cineplex.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.techup.minor_cineplex.dto.response.coupons.CouponPageResponse;
import com.techup.minor_cineplex.service.CouponsService;
import com.techup.minor_cineplex.utils.JwtUtils;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/promotions")
@RequiredArgsConstructor

public class CouponsController {

    private final CouponsService couponsService;

    @GetMapping("")
    public ResponseEntity<CouponPageResponse> listPromotions(
        @AuthenticationPrincipal Jwt jwt,
        @RequestParam(defaultValue = "1") Integer page,
        @RequestParam(required = false) Integer limitItems,
        @RequestParam(required = false) String partnerName
    ) {
        return ResponseEntity.ok(
            couponsService.listAllCoupons(
                page,
                limitItems,
                partnerName,
                jwt != null ? JwtUtils.extractUserId(jwt) : null
            )
        );
    }
}

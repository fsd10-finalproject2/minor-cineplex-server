package com.techup.minor_cineplex.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.techup.minor_cineplex.dto.response.coupons.CouponResponse;
import com.techup.minor_cineplex.service.CouponsService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/promotions")
@RequiredArgsConstructor
public class CouponsController {

    private final CouponsService couponsService;

    @GetMapping("/list")
    public ResponseEntity<List<CouponResponse>> listPromotions() {
        return ResponseEntity.ok(couponsService.listAllCoupons());
    }
}

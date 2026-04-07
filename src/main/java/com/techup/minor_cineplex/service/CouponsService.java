package com.techup.minor_cineplex.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.techup.minor_cineplex.dto.response.coupons.CouponResponse;
import com.techup.minor_cineplex.repository.CouponRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CouponsService {

    private final CouponRepository couponRepository;

    public List<CouponResponse> listAllCoupons() {
        return couponRepository.findAllWithCouponInfo().stream()
            .map(CouponResponse::from)
            .toList();
    }
}

package com.techup.minor_cineplex.dto.response.coupons;

import java.util.List;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CouponPageResponse {
    private final List<CouponResponse> data;
    private final Integer currentPage;
    private final Integer totalPage;
}

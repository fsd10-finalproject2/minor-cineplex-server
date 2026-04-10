package com.techup.minor_cineplex.service;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.techup.minor_cineplex.dto.response.coupons.CouponPageResponse;
import com.techup.minor_cineplex.dto.response.coupons.CouponResponse;
import com.techup.minor_cineplex.entity.Coupons;
import com.techup.minor_cineplex.repository.CouponRepository;
import com.techup.minor_cineplex.repository.UserCouponRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CouponsService {

    private static final int DEFAULT_LIMIT_ITEMS = 4;
    private static final int DEFAULT_PAGE_NUMBER = 1;

    private final CouponRepository couponRepository;
    private final UserCouponRepository userCouponRepository;

    public CouponPageResponse listAllCoupons(Integer page, Integer limitItems, String partnerName) {
        return listAllCoupons(page, limitItems, partnerName, null);
    }

    public CouponPageResponse listAllCoupons(Integer page, Integer limitItems, String partnerName, UUID userId) {
        int resolvedPage = (page == null || page < 1) ? DEFAULT_PAGE_NUMBER : page;
        int resolvedLimit = (limitItems == null || limitItems <= 0) ? DEFAULT_LIMIT_ITEMS : limitItems;
        PageRequest pageable = PageRequest.of(
            resolvedPage - 1,
            resolvedLimit,
            Sort.by(Sort.Direction.DESC, "createdAt")
        );
        Page<Coupons> couponPage = fetchActiveCoupons(pageable, partnerName);
        int totalPage = couponPage.getTotalPages();

        if (totalPage > 0 && resolvedPage > totalPage) {
            resolvedPage = totalPage;
            PageRequest lastPageable = PageRequest.of(
                resolvedPage - 1,
                resolvedLimit,
                Sort.by(Sort.Direction.DESC, "createdAt")
            );
            couponPage = fetchActiveCoupons(lastPageable, partnerName);
            totalPage = couponPage.getTotalPages();
        }

        Set<Integer> myCouponIds = (userId == null)
            ? Set.of()
            : userCouponRepository.findDistinctCouponIdsByUserId(userId).stream()
                .collect(Collectors.toSet());

        List<CouponResponse> data = couponPage.getContent().stream()
            .map(c -> CouponResponse.from(c, myCouponIds.contains(c.getId())))
            .toList();

        return CouponPageResponse.builder()
            .data(data)
            .currentPage(resolvedPage)
            .totalPage(totalPage)
            .build();
    }

    private Page<Coupons> fetchActiveCoupons(PageRequest pageable, String partnerName) {
        if (partnerName == null || partnerName.isBlank()) {
            return couponRepository.findAllByIsActiveTrue(pageable);
        }
        return couponRepository.findAllByIsActiveTrueAndCouponPartner_PartnerNameIgnoreCase(partnerName.trim(), pageable);
    }
}

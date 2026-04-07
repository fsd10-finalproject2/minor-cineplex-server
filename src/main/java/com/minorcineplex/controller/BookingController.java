package com.minorcineplex.controller;

import com.minorcineplex.dto.request.booking.BookingRequest;
import com.minorcineplex.dto.response.booking.BookingResponse;
import com.minorcineplex.entity.Booking;
import com.minorcineplex.mapper.BookingMapper;
import com.minorcineplex.service.BookingService;
import com.minorcineplex.utils.JwtUtils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/bookings")
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;
    private final BookingMapper bookingMapper;

    @PostMapping
    public ResponseEntity<BookingResponse> createBooking(
            @AuthenticationPrincipal Jwt jwt,
            @RequestBody @Valid BookingRequest request) {
        UUID userId = JwtUtils.extractUserId(jwt);
        Booking booking = bookingService.createBooking(userId, request.getShowtimeId(), request.getSeatIds());
        return ResponseEntity.ok(bookingMapper.toResponse(booking));
    }

    @GetMapping("/my")
    public ResponseEntity<List<BookingResponse>> getMyBookings(@AuthenticationPrincipal Jwt jwt) {
        UUID userId = JwtUtils.extractUserId(jwt);
        List<Booking> bookings = bookingService.getMyBookings(userId);
        return ResponseEntity.ok(bookings.stream()
                .map(bookingMapper::toResponse)
                .collect(Collectors.toList()));
    }
}

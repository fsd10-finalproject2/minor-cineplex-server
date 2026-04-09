package com.techup.minor_cineplex.controller;

import com.techup.minor_cineplex.dto.request.seat.SeatSelectionRequest;
import com.techup.minor_cineplex.entity.Seat;
import com.techup.minor_cineplex.entity.SeatSelection;
import com.techup.minor_cineplex.service.SeatService;
import com.techup.minor_cineplex.utils.JwtUtils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/seats")
@RequiredArgsConstructor
public class SeatController {

    private final SeatService seatService;

    @GetMapping("/theater/{theaterId}")
    public ResponseEntity<List<Seat>> getSeats(@PathVariable Long theaterId) {
        return ResponseEntity.ok(seatService.getSeatsByTheater(theaterId));
    }

    @GetMapping("/selections/{showtimeId}")
    public ResponseEntity<List<SeatSelection>> getSelections(@PathVariable Long showtimeId) {
        return ResponseEntity.ok(seatService.getCurrentSelections(showtimeId));
    }

    @PostMapping("/select")
    public ResponseEntity<SeatSelection> selectSeat(
            @AuthenticationPrincipal Jwt jwt,
            @RequestBody @Valid SeatSelectionRequest request) {
        UUID userId = JwtUtils.extractUserId(jwt);
        return ResponseEntity.ok(seatService.selectSeat(userId, request.getShowtimeId(), request.getSeatId()));
    }

    @PostMapping("/deselect")
    public ResponseEntity<Void> deselectSeat(
            @AuthenticationPrincipal Jwt jwt,
            @RequestBody @Valid SeatSelectionRequest request) {
        UUID userId = JwtUtils.extractUserId(jwt);
        seatService.deselectSeat(userId, request.getShowtimeId(), request.getSeatId());
        return ResponseEntity.ok().build();
    }
}

package com.techup.minor_cineplex.controller;

import com.techup.minor_cineplex.dto.response.CinemaResponse;
import com.techup.minor_cineplex.service.CinemaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cinemas")
@RequiredArgsConstructor
public class CinemaController {

    private final CinemaService cinemaService;

    @GetMapping
    public ResponseEntity<List<CinemaResponse>> getAllCinemas(
            @RequestParam(required = false) Double latitude,
            @RequestParam(required = false) Double longitude) {
        return ResponseEntity.ok(cinemaService.getAllCinemas(latitude, longitude));
    }

    @GetMapping("/city/{city}")
    public ResponseEntity<List<CinemaResponse>> getCinemasByCity(
            @PathVariable String city,
            @RequestParam(required = false) Double latitude,
            @RequestParam(required = false) Double longitude) {
        return ResponseEntity.ok(cinemaService.getCinemasByCity(city, latitude, longitude));
    }
}

package com.techup.minor_cineplex.service;

import com.techup.minor_cineplex.dto.response.CinemaResponse;
import com.techup.minor_cineplex.entity.Cinema;
import com.techup.minor_cineplex.repository.CinemaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CinemaServiceImpl implements CinemaService {

    private final CinemaRepository cinemaRepository;
    private static final double EARTH_RADIUS = 6371; // km

    @Override
    public List<CinemaResponse> getAllCinemas(Double userLat, Double userLng) {
        List<Cinema> cinemas = cinemaRepository.findAll();
        return processCinemas(cinemas, userLat, userLng);
    }

    @Override
    public List<CinemaResponse> getCinemasByCity(String city, Double userLat, Double userLng) {
        List<Cinema> cinemas = cinemaRepository.findByCity(city);
        return processCinemas(cinemas, userLat, userLng);
    }

    private List<CinemaResponse> processCinemas(List<Cinema> cinemas, Double userLat, Double userLng) {
        List<CinemaResponse> responses = cinemas.stream()
                .map(cinema -> mapToResponse(cinema, userLat, userLng))
                .collect(Collectors.toList());

        // Sort by distance if available, else by name
        if (userLat != null && userLng != null) {
            responses.sort((a, b) -> {
                if (a.getDistance() == null && b.getDistance() == null) {
                    return a.getName().compareToIgnoreCase(b.getName());
                }
                if (a.getDistance() == null) return 1;
                if (b.getDistance() == null) return -1;
                return Double.compare(a.getDistance(), b.getDistance());
            });
        } else {
            responses.sort((a, b) -> a.getName().compareToIgnoreCase(b.getName()));
        }

        return responses;
    }

    private CinemaResponse mapToResponse(Cinema cinema, Double userLat, Double userLng) {
        CinemaResponse response = CinemaResponse.builder()
                .id(cinema.getId())
                .name(cinema.getName())
                .address(cinema.getAddress())
                .city(cinema.getCity())
                .latitude(cinema.getLatitude())
                .longitude(cinema.getLongitude())
                .build();

        if (userLat != null && userLng != null && cinema.getLatitude() != null && cinema.getLongitude() != null) {
            response.setDistance(calculateDistance(userLat, userLng, cinema.getLatitude(), cinema.getLongitude()));
        }

        return response;
    }

    private double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(dLon / 2) * Math.sin(dLon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return EARTH_RADIUS * c;
    }
}

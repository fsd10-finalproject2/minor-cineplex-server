package com.techup.minor_cineplex.service;

import com.techup.minor_cineplex.dto.response.CinemaResponse;
import java.util.List;

public interface CinemaService {
    List<CinemaResponse> getAllCinemas(Double userLatitude, Double userLongitude);
    List<CinemaResponse> getCinemasByCity(String city, Double userLatitude, Double userLongitude);
}

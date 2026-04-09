package com.techup.minor_cineplex.service;

import com.techup.minor_cineplex.entity.Booking;
import java.util.List;
import java.util.UUID;

public interface BookingService {
    Booking createBooking(UUID userId, Long showtimeId, List<Long> seatIds);
    List<Booking> getMyBookings(UUID userId);
}

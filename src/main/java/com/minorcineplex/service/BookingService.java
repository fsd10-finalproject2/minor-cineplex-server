package com.minorcineplex.service;

import com.minorcineplex.entity.Booking;
import java.util.List;
import java.util.UUID;

public interface BookingService {
    Booking createBooking(UUID userId, Long showtimeId, List<Long> seatIds);
    List<Booking> getMyBookings(UUID userId);
}

package com.minorcineplex.mapper;

import com.minorcineplex.dto.response.booking.BookingResponse;
import com.minorcineplex.entity.Booking;
import org.springframework.stereotype.Component;
import java.util.stream.Collectors;

@Component
public class BookingMapper {

    public BookingResponse toResponse(Booking booking) {
        return BookingResponse.builder()
                .bookingId(booking.getId())
                .movieTitle(booking.getShowtime().getMovie().getTitle())
                .cinemaName(booking.getShowtime().getCinema().getName())
                .hallName(booking.getShowtime().getHallName())
                .startTime(booking.getShowtime().getStartTime())
                .status(booking.getStatus().name())
                .totalPrice(booking.getTotalPrice())
                .seatNumbers(booking.getTickets().stream()
                        .map(t -> t.getSeat().getSeatNumber())
                        .collect(Collectors.toList()))
                .build();
    }
}

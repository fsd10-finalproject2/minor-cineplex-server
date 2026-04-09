package com.techup.minor_cineplex.dto.request.seat;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class SeatSelectionRequest {
    @NotNull(message = "Showtime ID is required")
    private Long showtimeId;

    @NotNull(message = "Seat ID is required")
    private Long seatId;
}

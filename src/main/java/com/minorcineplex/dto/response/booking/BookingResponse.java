package com.minorcineplex.dto.response.booking;

import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class BookingResponse {
    private Long bookingId;
    private String movieTitle;
    private String cinemaName;
    private String hallName;
    private LocalDateTime startTime;
    private List<String> seatNumbers;
    private Double totalPrice;
    private String status;
}

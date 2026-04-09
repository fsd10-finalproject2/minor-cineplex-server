package com.techup.minor_cineplex.service;

import com.techup.minor_cineplex.entity.Seat;
import com.techup.minor_cineplex.entity.SeatSelection;
import java.util.List;
import java.util.UUID;

public interface SeatService {
    List<Seat> getSeatsByTheater(Long theaterId);
    List<SeatSelection> getCurrentSelections(Long showtimeId);
    SeatSelection selectSeat(UUID userId, Long showtimeId, Long seatId);
    void deselectSeat(UUID userId, Long showtimeId, Long seatId);
}

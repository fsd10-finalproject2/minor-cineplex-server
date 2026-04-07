package com.minorcineplex.service;

import com.minorcineplex.entity.Seat;
import com.minorcineplex.entity.SeatSelection;
import java.util.List;
import java.util.UUID;

public interface SeatService {
    List<Seat> getSeatsByTheater(Long theaterId);
    List<SeatSelection> getCurrentSelections(Long showtimeId);
    SeatSelection selectSeat(UUID userId, Long showtimeId, Long seatId);
    void deselectSeat(UUID userId, Long showtimeId, Long seatId);
}

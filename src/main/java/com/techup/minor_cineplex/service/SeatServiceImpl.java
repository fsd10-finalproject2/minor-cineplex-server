package com.techup.minor_cineplex.service;

import com.techup.minor_cineplex.entity.Seat;
import com.techup.minor_cineplex.entity.SeatSelection;
import com.techup.minor_cineplex.exception.AppException;
import com.techup.minor_cineplex.exception.ErrorCode;
import com.techup.minor_cineplex.exception.ResourceNotFoundException;
import com.techup.minor_cineplex.repository.SeatRepository;
import com.techup.minor_cineplex.repository.SeatSelectionRepository;
import com.techup.minor_cineplex.repository.TicketRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SeatServiceImpl implements SeatService {

    private final SeatRepository seatRepository;
    private final SeatSelectionRepository selectionRepository;
    private final TicketRepository ticketRepository;

    @Override
    public List<Seat> getSeatsByTheater(Long theaterId) {
        return seatRepository.findByTheaterId(theaterId);
    }

    @Override
    public List<SeatSelection> getCurrentSelections(Long showtimeId) {
        return selectionRepository.findByShowtimeId(showtimeId);
    }

    @Override
    @Transactional
    public SeatSelection selectSeat(UUID userId, Long showtimeId, Long seatId) {
        // 1. Check if seat exists
        if (!seatRepository.existsById(seatId)) {
            throw new ResourceNotFoundException("Seat not found");
        }

        // 2. Check if seat is already officially booked for this showtime
        // Note: For simplicity, we check tickets directly since we don't have a status on Seat for a specific showtime
        boolean isBooked = ticketRepository.findAll().stream()
                .anyMatch(t -> t.getSeat().getId().equals(seatId) && t.getBooking().getShowtime().getId().equals(showtimeId));
        
        if (isBooked) {
            throw new AppException(ErrorCode.SEAT_ALREADY_BOOKED);
        }

        // 3. Check if seat is currently selected by someone else
        List<SeatSelection> activeSelections = selectionRepository.findByShowtimeId(showtimeId);
        boolean isSelectedByOther = activeSelections.stream()
                .anyMatch(s -> s.getSeatId().equals(seatId) && !s.getUserId().equals(userId) && s.getExpiresAt().isAfter(LocalDateTime.now()));

        if (isSelectedByOther) {
            throw new AppException(ErrorCode.SEAT_ALREADY_SELECTED);
        }

        // 4. Create or update selection
        SeatSelection selection = selectionRepository.findByShowtimeId(showtimeId).stream()
                .filter(s -> s.getUserId().equals(userId) && s.getSeatId().equals(seatId))
                .findFirst()
                .orElse(SeatSelection.builder()
                        .userId(userId)
                        .showtimeId(showtimeId)
                        .seatId(seatId)
                        .selectedAt(LocalDateTime.now())
                        .build());

        selection.setExpiresAt(LocalDateTime.now().plusMinutes(10)); // 10 minute hold
        return selectionRepository.save(selection);
    }

    @Override
    @Transactional
    public void deselectSeat(UUID userId, Long showtimeId, Long seatId) {
        selectionRepository.findByShowtimeId(showtimeId).stream()
                .filter(s -> s.getUserId().equals(userId) && s.getSeatId().equals(seatId))
                .forEach(selectionRepository::delete);
    }
}

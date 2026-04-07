package com.minorcineplex.service;

import com.minorcineplex.entity.*;
import com.minorcineplex.enums.BookingStatus;
import com.minorcineplex.exception.AppException;
import com.minorcineplex.exception.ErrorCode;
import com.minorcineplex.exception.ResourceNotFoundException;
import com.minorcineplex.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;
    private final TicketRepository ticketRepository;
    private final UserRepository userRepository;
    private final ShowtimeRepository showtimeRepository;
    private final SeatRepository seatRepository;
    private final SeatSelectionRepository selectionRepository;

    @Override
    @Transactional
    public Booking createBooking(UUID userId, Long showtimeId, List<Long> seatIds) {
        // 1. Fetch User and Showtime
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        Showtime showtime = showtimeRepository.findById(showtimeId)
                .orElseThrow(() -> new ResourceNotFoundException("Showtime not found"));

        // 2. Fetch and Validate Seats
        List<Seat> seats = seatRepository.findAllById(seatIds);
        if (seats.size() != seatIds.size()) {
            throw new ResourceNotFoundException("One or more seats not found");
        }

        // 3. Verify that each seat is currently "held" by this user in seat_selections
        // This ensures the user isn't booking someone else's selection
        List<SeatSelection> userSelections = selectionRepository.findByUserId(userId).stream()
                .filter(s -> s.getShowtimeId().equals(showtimeId) && seatIds.contains(s.getSeatId()))
                .collect(Collectors.toList());

        if (userSelections.size() != seatIds.size()) {
            throw new AppException(ErrorCode.SEAT_NOT_SELECTED_BY_USER);
        }

        // 4. Create Booking
        Booking booking = Booking.builder()
                .user(user)
                .showtime(showtime)
                .bookingTime(LocalDateTime.now())
                .status(BookingStatus.PAID) // Assuming payment is already handled for now
                .totalPrice(0.0) // Will calculate below
                .build();

        Booking savedBooking = bookingRepository.save(booking);

        // 5. Create Tickets and Calculate Total Price
        double totalPrice = 0.0;
        List<Ticket> tickets = new ArrayList<>();
        for (Seat seat : seats) {
            double seatPrice = 150.0; // Mock price, in production this might come from SeatType or Showtime
            Ticket ticket = Ticket.builder()
                    .booking(savedBooking)
                    .seat(seat)
                    .price(seatPrice)
                    .build();
            tickets.add(ticket);
            totalPrice += seatPrice;
        }
        ticketRepository.saveAll(tickets);

        savedBooking.setTotalPrice(totalPrice);
        savedBooking.setTickets(tickets);

        // 6. Clear current selections for these seats
        selectionRepository.deleteAll(userSelections);

        return bookingRepository.save(savedBooking);
    }

    @Override
    public List<Booking> getMyBookings(UUID userId) {
        return bookingRepository.findByUserId(userId);
    }
}

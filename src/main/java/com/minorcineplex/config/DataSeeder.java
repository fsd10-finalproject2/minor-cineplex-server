package com.minorcineplex.config;

import com.minorcineplex.entity.*;
import com.minorcineplex.enums.SeatStatus;
import com.minorcineplex.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class DataSeeder implements CommandLineRunner {

    private final MovieRepository movieRepository;
    private final CinemaRepository cinemaRepository;
    private final SeatRepository seatRepository;
    private final ShowtimeRepository showtimeRepository;

    @Override
    public void run(String... args) throws Exception {
        if (movieRepository.count() > 0) {
            log.info("Database already seeded. Skipping...");
            return;
        }

        log.info("Seeding database with mock data...");

        // 1. Create Movie
        Movie movie = Movie.builder()
                .title("The Dark Knight")
                .description("Batman raises the stakes in his war on crime.")
                .durationMinutes(152)
                .posterUrl("https://image.tmdb.org/t/p/w500/qJ2tW6WMUDux911r6m7haRef0WH.jpg")
                .rating("PG-13")
                .genres(List.of("Action", "Crime", "Drama"))
                .build();
        movieRepository.save(movie);

        // 2. Create Cinema
        Cinema cinema = Cinema.builder()
                .name("Minor Cineplex Arkham")
                .address("123 Wayne Tower, Gotham City")
                .city("Gotham")
                .build();
        cinemaRepository.save(cinema);

        // 3. Create Showtime
        Showtime showtime = Showtime.builder()
                .movie(movie)
                .cinema(cinema)
                .startTime(LocalDateTime.now().plusHours(2))
                .endTime(LocalDateTime.now().plusHours(5))
                .hallName("Hall 1")
                .build();
        showtimeRepository.save(showtime);

        // 4. Create Seats for Theater 1 (mapped to our logic)
        List<Seat> seats = new ArrayList<>();
        String[] rows = {"A", "B", "C", "D", "E"};
        for (String row : rows) {
            for (int col = 1; col <= 10; col++) {
                seats.add(Seat.builder()
                        .rowCode(row)
                        .colNumber(col)
                        .seatNumber(row + col)
                        .seatType("REGULAR")
                        .theaterId(1L)
                        .status(SeatStatus.AVAILABLE)
                        .build());
            }
        }
        seatRepository.saveAll(seats);

        log.info("Seeding complete.");
    }
}

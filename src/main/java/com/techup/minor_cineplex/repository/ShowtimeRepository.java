package com.techup.minor_cineplex.repository;

import com.techup.minor_cineplex.entity.Showtime;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDateTime;
import java.util.List;

public interface ShowtimeRepository extends JpaRepository<Showtime, Long> {
    List<Showtime> findByMovieId(Long movieId);
    List<Showtime> findByCinemaIdAndStartTimeAfter(Long cinemaId, LocalDateTime now);
}

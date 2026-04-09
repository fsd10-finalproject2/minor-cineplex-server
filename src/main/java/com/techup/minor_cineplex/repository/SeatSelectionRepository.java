package com.techup.minor_cineplex.repository;

import com.techup.minor_cineplex.entity.SeatSelection;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface SeatSelectionRepository extends JpaRepository<SeatSelection, Long> {
    List<SeatSelection> findByShowtimeId(Long showtimeId);
    List<SeatSelection> findByUserId(UUID userId);
    void deleteByExpiresAtBefore(LocalDateTime now);
}

package com.minorcineplex.repository;

import com.minorcineplex.entity.Seat;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface SeatRepository extends JpaRepository<Seat, Long> {
    List<Seat> findByTheaterId(Long theaterId);
}

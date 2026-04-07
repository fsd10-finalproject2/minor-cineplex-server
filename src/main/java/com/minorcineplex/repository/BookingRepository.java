package com.minorcineplex.repository;

import com.minorcineplex.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.UUID;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findByUserId(UUID userId);
}

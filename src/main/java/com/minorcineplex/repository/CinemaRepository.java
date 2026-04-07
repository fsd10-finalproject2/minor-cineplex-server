package com.minorcineplex.repository;

import com.minorcineplex.entity.Cinema;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CinemaRepository extends JpaRepository<Cinema, Long> {
    List<Cinema> findByCity(String city);
}

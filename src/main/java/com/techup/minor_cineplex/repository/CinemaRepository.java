package com.techup.minor_cineplex.repository;

import com.techup.minor_cineplex.entity.Cinema;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CinemaRepository extends JpaRepository<Cinema, Long> {
    List<Cinema> findByCity(String city);
}

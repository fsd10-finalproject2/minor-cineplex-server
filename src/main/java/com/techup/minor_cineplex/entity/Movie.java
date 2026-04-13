package com.techup.minor_cineplex.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Entity
@Table(name = "movies")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    private Integer durationMinutes;

    private String posterUrl;

    private String trailerUrl;

    private String rating; // e.g., G, PG, PG-13, R

    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> genres;

    private String language;

    private java.time.LocalDate releaseDate;
}

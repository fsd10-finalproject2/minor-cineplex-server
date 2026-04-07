package com.minorcineplex.entity;

import java.time.LocalDateTime;
import java.util.UUID;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "seat_selections")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SeatSelection {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long showtimeId;

    @Column(nullable = false)
    private UUID userId;

    @Column(nullable = false)
    private Long seatId;

    @Column(nullable = false)
    private LocalDateTime selectedAt;

    @Column(nullable = false)
    private LocalDateTime expiresAt;
}

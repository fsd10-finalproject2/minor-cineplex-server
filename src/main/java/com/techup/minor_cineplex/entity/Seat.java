package com.techup.minor_cineplex.entity;

import com.techup.minor_cineplex.enums.SeatStatus;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "seats")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Seat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String rowCode;

    @Column(nullable = false)
    private Integer colNumber;

    @Column(nullable = false)
    private String seatNumber;

    @Column(nullable = false)
    private String seatType; // e.g., REGULAR, VIP

    @Column(nullable = false)
    private Long theaterId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SeatStatus status;
}

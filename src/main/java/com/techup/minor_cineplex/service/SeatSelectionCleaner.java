package com.techup.minor_cineplex.service;

import com.techup.minor_cineplex.repository.SeatSelectionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class SeatSelectionCleaner {

    private final SeatSelectionRepository selectionRepository;

    /**
     * Runs every minute to clear expired seat selections from the database.
     * This ensures that seats aren't permanently locked by abandoned sessions.
     */
    @Scheduled(fixedRate = 60000)
    @Transactional
    public void clearExpiredSelections() {
        log.info("Running seat selection cleaner at {}", LocalDateTime.now());
        selectionRepository.deleteByExpiresAtBefore(LocalDateTime.now());
    }
}

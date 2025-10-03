package com.example.demo.repository;

import com.example.demo.entity.WatchHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface WatchHistoryRepository extends JpaRepository<WatchHistory, Long> {
    List<WatchHistory> findByUserId(Long userId);
    Optional<WatchHistory> findByUserIdAndTmdbId(Long userId, Long movieId);
    List<WatchHistory> findByUserIdAndWatchedAtAfter(Long userId, LocalDateTime fromDate);
}
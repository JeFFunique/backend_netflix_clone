package com.example.demo.service;

import com.example.demo.entity.Movie;
import com.example.demo.entity.WatchHistory;
import com.example.demo.repository.WatchHistoryRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class WatchHistoryService {
    private final WatchHistoryRepository watchHistoryRepository;
    public WatchHistoryService(WatchHistoryRepository watchHistoryRepository) {
        this.watchHistoryRepository = watchHistoryRepository;
    }
    public WatchHistory saveWatchHistory(Long userId, Long tmdbId, int watchTime, String genreIds) {
        Optional<WatchHistory> existing = watchHistoryRepository.findByUserIdAndTmdbId(userId, tmdbId);
        if(existing.isPresent()){
            WatchHistory history = existing.get();
            history.setWatchTime(history.getWatchTime() + watchTime);
            history.setWatchedAt(Instant.now());
            return watchHistoryRepository.save(history);
        }

        WatchHistory history = new WatchHistory();
        history.setUserId(userId);
        history.setTmdbId(tmdbId);
        history.setGenreIds(genreIds);
        history.setWatchTime(watchTime);
        history.setWatchedAt(Instant.now());
        return watchHistoryRepository.save(history);
    }
    public String getMostWatchedGenre(Long userId, Instant fromDate, Instant toDate) {
        List<WatchHistory> history = watchHistoryRepository.findByUserId(userId);
        List<WatchHistory> filtered = history.stream()
                .filter(h -> !h.getWatchedAt().isBefore(fromDate) && !h.getWatchedAt().isAfter(toDate))
                .filter(h -> !h.getGenreIds().equals("0"))
                .toList();

        Map<String, Integer> genreWatchTime = new HashMap<>();
        for(WatchHistory h :filtered) {
            String[] genres = h.getGenreIds().split(",");
            for(String g : genres) {
                genreWatchTime.merge(g.trim(), h.getWatchTime(), Integer::sum);
            }
        }
        return genreWatchTime.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse(null);
    }
}

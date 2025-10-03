package com.example.demo.controller;


import com.example.demo.dto.GenreRecommendationRequest;
import com.example.demo.dto.WatchHistoryRequest;
import com.example.demo.entity.Movie;
import com.example.demo.entity.WatchHistory;
import com.example.demo.repository.WatchHistoryRepository;
import com.example.demo.service.WatchHistoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/watch")
public class WatchHistoryController {
    private final WatchHistoryRepository watchHistoryRepository;
    private final WatchHistoryService watchHistoryService;
    public WatchHistoryController(WatchHistoryRepository watchHistoryRepository, WatchHistoryService watchHistoryService) {
        this.watchHistoryRepository = watchHistoryRepository;
        this.watchHistoryService = watchHistoryService;
    }
    @PostMapping("/save")
    public WatchHistory saveWatchHistory(@RequestBody WatchHistoryRequest request){
    return watchHistoryService.saveWatchHistory(
            request.getUserId(),
            request.getTmdbId(),
            request.getWatchTime(),
            request.getGenreIds()
    );
    }
    @PostMapping("/recommendations/genre")
    public ResponseEntity<String> getMostWatchedGenre(@RequestBody GenreRecommendationRequest request) {
       String result = watchHistoryService.getMostWatchedGenre(
                request.getUserId(),
                request.getFromDate(),
                request.getToDate()
        );
        return ResponseEntity.ok(result);
    }

}

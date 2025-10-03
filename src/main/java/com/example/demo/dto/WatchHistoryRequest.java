package com.example.demo.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class WatchHistoryRequest {
    private Long userId;
    private Long tmdbId;
    private String genreIds;
    private int watchTime;
}

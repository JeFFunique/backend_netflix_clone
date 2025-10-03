package com.example.demo.dto;

import lombok.Data;

import java.time.Instant;

@Data
public class GenreRecommendationRequest {
    private Long userId;
    private Instant fromDate;
    private Instant toDate;
}

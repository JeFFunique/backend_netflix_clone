package com.example.demo.dto;

import lombok.Data;
import java.util.List;

@Data
public class TmdbResponse {
    private int page;
    private List<TmdbMovie> results;
    private int total_pages;
    private int total_results;
}
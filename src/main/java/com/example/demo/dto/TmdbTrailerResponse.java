package com.example.demo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class TmdbTrailerResponse {
    private long id;
    private List<TmdbTrailerVideo> results;
}

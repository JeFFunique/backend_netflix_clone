package com.example.demo.dto;

import com.example.demo.entity.Movie;
import lombok.Data;

import java.util.List;

@Data
public class MovieClassDeduplicated {
    private List<Movie> favorites;
    private List<Movie> userBased;
    private List<Movie> trending;
    private List<Movie> generalBased;
}

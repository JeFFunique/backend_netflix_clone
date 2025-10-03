package com.example.demo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;


@Data
public class TmdbMovie {
    private Long id;
    private String title;
    @JsonProperty("poster_path")
    private String posterPath;
    @JsonProperty("backdrop_path")
    private String backgroundPath;
    private String overview;
    @JsonProperty("genre_ids")
    private List<Long> genreIds;
    public String getFullPosterIrl() {
        if(posterPath==null){
            return null;
        }
        return "https://image.tmdb.org/t/p/w500" + posterPath;
    }
    public String getFullBackdropIrl() {
        if(backgroundPath ==null){
            return null;
        }
        return "https://image.tmdb.org/t/p/original" + backgroundPath;
    }
    public String getGenreIdsToString() {
        return genreIds == null ? "0" : genreIds
                .stream()
                .map(genre -> genre.toString())
                .collect(Collectors.joining(","));
    }
}
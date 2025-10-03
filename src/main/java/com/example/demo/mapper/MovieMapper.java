package com.example.demo.mapper;

import com.example.demo.dto.TmdbMovie;
import com.example.demo.dto.TmdbTrailerVideo;
import com.example.demo.entity.Movie;

public class MovieMapper {
    public static Movie toEntity(TmdbMovie dto) {
        Movie movie = new Movie();
        movie.setTmdbId(dto.getId());
        movie.setTitle(dto.getTitle());
        movie.setOverview(dto.getOverview());
        movie.setImageUrl(dto.getFullPosterIrl());
        movie.setBackgroundImageUrl(dto.getFullBackdropIrl());
        movie.setGenreIds(dto.getGenreIdsToString());
        return movie;
    }
}

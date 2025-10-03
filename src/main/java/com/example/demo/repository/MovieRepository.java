package com.example.demo.repository;


import com.example.demo.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.demo.dto.TmdbMovie;

import java.util.List;

public interface MovieRepository extends JpaRepository<Movie, Long> {
    List<Movie> findByCategory(String Category);
    boolean existsByTmdbId(Long tmdbId);
    Movie findByTmdbId(Long tmdbId);
}

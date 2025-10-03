package com.example.demo.repository;


import com.example.demo.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.demo.dto.TmdbMovie;

import java.util.List;
import java.util.Optional;

public interface MovieRepository extends JpaRepository<Movie, Long> {
    List<Movie> findByCategory(String Category);
    boolean existsByTmdbId(Long tmdbId);
    Optional<Movie> findByTmdbId(Long tmdbId);
}

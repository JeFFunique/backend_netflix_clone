package com.example.demo.repository;

import com.example.demo.entity.Favorite;
import com.example.demo.entity.User;
import com.example.demo.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FavoriteRepository extends JpaRepository<Favorite, Long> {
    List<Favorite> findByUser(User user);
    Optional<Favorite> findByUserAndMovie(User user, Movie movie);
    void deleteByUserAndMovie(User user, Movie movie);
}
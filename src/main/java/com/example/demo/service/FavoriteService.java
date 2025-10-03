package com.example.demo.service;


import com.example.demo.entity.Favorite;
import com.example.demo.entity.Movie;
import com.example.demo.entity.User;
import com.example.demo.repository.FavoriteRepository;
import com.example.demo.repository.MovieRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FavoriteService {
    private final FavoriteRepository favoriteRepository;
    private final UserRepository userRepository;
    private final MovieRepository movieRepository;

    public FavoriteService(FavoriteRepository favoriteRepository, UserRepository userRepository, MovieRepository movieRepository) {
        this.favoriteRepository = favoriteRepository;
        this.userRepository = userRepository;
        this.movieRepository = movieRepository;
    }
    public List<Favorite> getUserFavorites(Long userId) {
        User user = userRepository.findById(userId).orElseThrow();
        return favoriteRepository.findByUser(user);
    }

    public Favorite addFavorite(Long userId, Movie movie) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new RuntimeException("User not found with id: " + userId)
        );
        Movie persistedMovie = movieRepository.findByTmdbId(movie.getTmdbId())
                .orElseGet(() -> movieRepository.save(movie));
        return favoriteRepository.findByUserAndMovie(user, persistedMovie)
                .orElseGet(() -> favoriteRepository.save(new Favorite() {{
                    setUser(user);
                    setMovie(persistedMovie);
                }}));
    }

    public void removeFavorite(Long userId, Long movieId) {
        User user = userRepository.findById(userId).orElseThrow();
        Movie movie = movieRepository.findById(movieId).orElseThrow();
        favoriteRepository.deleteByUserAndMovie(user, movie);
    }
}
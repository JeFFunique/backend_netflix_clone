package com.example.demo.controller;


import com.example.demo.entity.Favorite;
import com.example.demo.entity.Movie;
import com.example.demo.service.FavoriteService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/api/favorites")
public class FavoriteController {
    private final FavoriteService favoriteService;
    public FavoriteController(FavoriteService favoriteService) {
        this.favoriteService = favoriteService;
    }
    @PostMapping("/add/{userId}")
    public Favorite addFavorite(@PathVariable("userId") Long userId, @RequestBody Movie movie) {
        return favoriteService.addFavorite(userId, movie);
    }

    @DeleteMapping("/{userId}/{movieId}")
    public void removeFavorite(@PathVariable("userId") Long userId, @PathVariable("movieId") Long movieId) {
        favoriteService.removeFavorite(userId, movieId);
    }
}

package com.example.demo.controller;

import com.example.demo.dto.MovieClassDeduplicated;
import com.example.demo.dto.TmdbResponse;
import com.example.demo.dto.TmdbTrailerVideo;
import com.example.demo.entity.Movie;
import com.example.demo.service.MovieService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.file.Path;
import java.util.List;

@RestController
@RequestMapping("/api/movies")
public class MovieController {
    private final MovieService movieService;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }
 @GetMapping("/all/{genreUser}/{genreGeneral}/{userId}")
 public MovieClassDeduplicated getAllUniqueList(
         @PathVariable("genreUser") int genreId_User,
         @PathVariable("genreGeneral") int genreId_General,
         @PathVariable("userId") Long userId) {
 return movieService.getAllUniqueList(genreId_User, genreId_General, userId);
 }
    @GetMapping("/movie_most_popular")
    public Movie getMostPopularMovie() {
        return movieService.getMostPopularMovie();
    }
    @GetMapping("/trailer/{id}")
    public TmdbTrailerVideo getMovieTrailer(@PathVariable int id) {
        return movieService.getMovieTrailer(id);
    }
    @GetMapping("/only/movies")
    public List<Movie> getOnlyMovies(){
        return movieService.getOnlyMovies();
    }
    @GetMapping("/only/series")
    public List<Movie> getOnlySeries(){
        return movieService.getOnlySeries();
    }
    @GetMapping("/search/{search}")
    public List<Movie> getFilmSearched(@PathVariable("search") String search) {
        return movieService.getFilmSearched(search);
    }
    @PostMapping("/sync/trending/{Category}/")
    public List<Movie> saveTrendingMoviesToDb(@PathVariable String Category) {
        return movieService.saveTrendingMoviesToDb(Category);
    }
    @PostMapping("sync/add_movie")
    public Movie addFavoriteToDb(@RequestBody Movie movie) {
        return movieService.addFavoriteToDb(movie);
    }
    @PostMapping("/sync/user_recommendations/genre/{genreId}")
    public List<Movie> saveUserRecommendationMoviesToDb(@PathVariable int genreId, String Category) {
        return movieService.saveUserRecommendationMoviesToDb(genreId, Category);
    }
    @PostMapping("/sync/general_recommendations/genre/{genreId}")
    public List<Movie> saveGeneralRecommendationMoviesToDb(@PathVariable int genreId, String Category) {
        return movieService.saveGeneralRecommendationMoviesToDb(genreId, Category);
    }

}
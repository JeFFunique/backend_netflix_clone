package com.example.demo.service;

import com.example.demo.dto.TmdbMovie;
import com.example.demo.dto.TmdbResponse;
import com.example.demo.dto.TmdbTrailerResponse;
import com.example.demo.dto.TmdbTrailerVideo;
import com.example.demo.dto.MovieClassDeduplicated;
import com.example.demo.entity.Favorite;
import com.example.demo.entity.Movie;
import com.example.demo.entity.User;
import com.example.demo.mapper.MovieMapper;
import com.example.demo.repository.FavoriteRepository;
import com.example.demo.repository.MovieRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.sql.Array;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class MovieService {
    private final MovieRepository movieRepository;
    private final WebClient webclient;
    private final FavoriteRepository favoriteRepository;
    private final UserRepository userRepository;

    public MovieService(MovieRepository movieRepository,
                        @Qualifier("tmdbWebClient") WebClient tmdbWebClient,
                        FavoriteRepository favoriteRepository, UserRepository userRepository) {
        this.movieRepository = movieRepository;
        this.webclient = tmdbWebClient;
        this.favoriteRepository = favoriteRepository;
        this.userRepository = userRepository;
    }

    public List<Movie> getFavorisMovie(Long userId) {
        if(userId==null) {
            return List.of();
        }
            User user = userRepository.findById(userId).orElseThrow();
            List<Favorite> favorites = favoriteRepository.findByUser(user);
            return favorites.stream()
                    .map(Favorite::getMovie)
                    .collect(Collectors.toList());
    }
    public List<Movie> getTrendingMovies() {
        TmdbResponse response = webclient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/trending/movie/day")
                        .queryParam("page", 5)
                        .build())
                .retrieve()
                .bodyToMono(TmdbResponse.class)
                .block();
        return response.getResults()
                .stream()
                .limit(20)
                .map(MovieMapper::toEntity)
                .toList();
    }

    public List<Movie> getUserRecommendationsMovies(int genreId) {
        TmdbResponse response = webclient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/discover/movie")
                        .queryParam("with_genres", genreId)
                        .queryParam("page", 1)
                        .build())
                .retrieve()
                .bodyToMono(TmdbResponse.class)
                .block();
       return response.getResults()
                .stream()
                .limit(20)
                .map(MovieMapper::toEntity)
                .toList();

    }

    public List<Movie> getGeneralRecommendationMovies(int genreId) {
        TmdbResponse response = webclient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/discover/movie")
                        .queryParam("with_genres", genreId)
                        .queryParam("page", 1)
                        .build())
                .retrieve()
                .bodyToMono(TmdbResponse.class)
                .block();
        return response.getResults()
                .stream()
                .limit(20)
                .map(MovieMapper::toEntity)
                .toList();

    }
    public Movie getMostPopularMovie() {
        TmdbResponse response = webclient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/movie/popular")
                        .build())
                .retrieve()
                .bodyToMono(TmdbResponse.class)
                .block();
        return response.getResults()
                .stream()
                .findFirst()
                .map(MovieMapper::toEntity)
                .orElse(null);
    }
    public TmdbTrailerVideo getMovieTrailer(int id) {
        TmdbTrailerResponse response = webclient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/movie/{id}/videos")
                        .build(id))
                .retrieve()
                .bodyToMono(TmdbTrailerResponse.class)
                .block();
        return response.getResults()
                .stream()
                .findFirst()
                .orElse(null);
    }
    public List<Movie> getOnlyMovies() {
        List<Movie> allMovies = new ArrayList<>();
        for(int page = 1; page<=3; page++) {
            final int currentPage = page;
            TmdbResponse response = webclient.get()
                    .uri(uriBuilder -> uriBuilder
                            .path("/movie/popular")
                            .queryParam("page", currentPage)
                            .build())
                    .retrieve()
                    .bodyToMono(TmdbResponse.class)
                    .block();
            allMovies.addAll(
                    response.getResults()
                            .stream()
                            .map(MovieMapper::toEntity)
                            .limit(60)
                            .toList()
            );
        }
        return allMovies;
    }
    public List<Movie> getOnlySeries() {
        List<Movie> allMovies = new ArrayList<>();
        for(int page = 1; page<=3; page++) {
            final int currentPage = page;
            TmdbResponse response = webclient.get()
                    .uri(uriBuilder -> uriBuilder
                            .path("/tv/popular")
                            .queryParam("page", currentPage)
                            .build())
                    .retrieve()
                    .bodyToMono(TmdbResponse.class)
                    .block();
            allMovies.addAll(
                    response.getResults()
                            .stream()
                            .map(MovieMapper::toEntity)
                            .limit(60)
                            .toList()
            );
        }
        return allMovies;
    }
public List<Movie> getFilmSearched(String search) {
    TmdbResponse response = webclient.get()
            .uri(uriBuilder -> uriBuilder
                    .path("/search/movie")
                    .queryParam("query", search)
                    .build())
            .retrieve()
            .bodyToMono(TmdbResponse.class)
            .block();

    return response.getResults()
            .stream()
            .filter(m -> m.getFullPosterIrl() != null && !m.getFullPosterIrl().isBlank())
            .map(MovieMapper::toEntity)
            .toList();
}
    public List<Movie> saveGeneralRecommendationMoviesToDb(int genreId, String Category) {
        List<Movie> moviesSaved = getGeneralRecommendationMovies(genreId);
        moviesSaved.forEach(m -> m.setCategory("GENERAL_RECOMMENDATIONS"));
        return movieRepository.saveAll(moviesSaved);

    }
    public List<Movie> saveUserRecommendationMoviesToDb(int genreId, String Category) {
        List<Movie> moviesSaved = getUserRecommendationsMovies(genreId);
        moviesSaved.forEach(m -> m.setCategory("USER_RECOMMENDATIONS"));
        return movieRepository.saveAll(moviesSaved);

    }
    public List<Movie> saveTrendingMoviesToDb(String Category) {
        List<Movie> moviesSaved = getTrendingMovies();
        moviesSaved.forEach(m -> m.setCategory("TRENDING"));
        return movieRepository.saveAll(moviesSaved);
    }
    public MovieClassDeduplicated getAllUniqueList(int genreId_user, int genreId_general, Long userId) {
        List<Movie> favoritesRaw = getFavorisMovie(userId);
        List<Movie> userBasedRaw = getUserRecommendationsMovies(genreId_user);
        List<Movie> trendingRaw = getTrendingMovies();
        List<Movie> generalBasedRaw = getGeneralRecommendationMovies(genreId_general);
        MovieClassDeduplicated result = new MovieClassDeduplicated();
        Map<Long, Movie> merged = Stream.of(favoritesRaw, userBasedRaw, trendingRaw, generalBasedRaw)
                .flatMap(List::stream)
                .collect(Collectors.toMap(
                        Movie::getTmdbId,
                        m->m,
                        (m1,m2) -> m1
                ));

        Set<Long> usedIds = new HashSet<>();
        List<Movie> favorites = favoritesRaw.stream()
                .filter(m -> merged.containsKey(m.getTmdbId()))
                .filter(m -> usedIds.add(m.getTmdbId()))
                .toList();
        List<Movie> userBased = userBasedRaw.stream()
                .filter(m -> merged.containsKey(m.getTmdbId()))
                .filter(m -> usedIds.add(m.getTmdbId()))
                .toList();
        List<Movie> trending = trendingRaw.stream()
                .filter(m -> merged.containsKey(m.getTmdbId()))
                .filter(m -> usedIds.add(m.getTmdbId()))
                .toList();
        List<Movie> general = generalBasedRaw.stream()
                .filter(m -> merged.containsKey(m.getTmdbId()))
                .filter(m -> usedIds.add(m.getTmdbId()))
                .toList();
        result.setFavorites(favorites);
        result.setUserBased(userBased);
        result.setTrending(trending);
        result.setGeneralBased(general);
        return result;
    }
}



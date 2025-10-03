package com.example.demo.entity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.util.Arrays;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true, nullable = false)
    private Long tmdbId;
    private String title;
    @Column(length = 2000)
    private String overview;
    @Column(length = 1000)
    private String imageUrl;
    private String backgroundImageUrl;
    private String category;
    @Column(length = 500) // enough for a list of ids
    private String genreIds;
    public List<Long> getGenreIdsAsList() {
        if (genreIds == null || genreIds.isBlank()) return List.of();
        return Arrays.stream(genreIds.split(","))
                .map(Long::valueOf)
                .toList();
    }
}

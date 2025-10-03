package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"userId", "tmdbId"}))
@Data
public class WatchHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userId;       // who watched
    private Long tmdbId;       // which movie/series
    private String genreIds;     // e.g. "28,878" (Action, Sci-Fi)
    private int watchTime; // in seconds
    @Column(name = "watched_at")
    private Instant watchedAt; // when
}

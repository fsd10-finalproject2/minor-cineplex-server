package com.techup.minor_cineplex.controller;

import com.techup.minor_cineplex.dto.request.movie.MovieSearchCriteria;
import com.techup.minor_cineplex.entity.Movie;
import com.techup.minor_cineplex.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/movies")
@RequiredArgsConstructor
public class MovieController {

    private final MovieService movieService;

    @GetMapping
    public ResponseEntity<List<Movie>> getAllMovies() {
        return ResponseEntity.ok(movieService.getAllMovies());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Movie> getMovieById(@PathVariable Long id) {
        return ResponseEntity.ok(movieService.getMovieById(id));
    }

    @GetMapping("/search")
    public ResponseEntity<List<Movie>> searchMovies(@RequestParam String query) {
        return ResponseEntity.ok(movieService.searchMovies(query));
    }

    @GetMapping("/filter")
    public ResponseEntity<List<Movie>> filterMovies(MovieSearchCriteria criteria) {
        return ResponseEntity.ok(movieService.searchMovies(criteria));
    }
}

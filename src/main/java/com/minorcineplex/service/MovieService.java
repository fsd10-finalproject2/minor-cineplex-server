package com.minorcineplex.service;

import com.minorcineplex.entity.Movie;
import java.util.List;

public interface MovieService {
    List<Movie> getAllMovies();
    Movie getMovieById(Long id);
    List<Movie> searchMovies(String query);
}

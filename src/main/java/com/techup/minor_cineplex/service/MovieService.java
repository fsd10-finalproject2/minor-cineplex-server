package com.techup.minor_cineplex.service;

import com.techup.minor_cineplex.entity.Movie;
import java.util.List;

public interface MovieService {
    List<Movie> getAllMovies();
    Movie getMovieById(Long id);
    List<Movie> searchMovies(String query);
}

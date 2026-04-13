package com.techup.minor_cineplex.service;

import com.techup.minor_cineplex.entity.Movie;
import com.techup.minor_cineplex.exception.ResourceNotFoundException;
import com.techup.minor_cineplex.dto.request.movie.MovieSearchCriteria;
import com.techup.minor_cineplex.repository.MovieRepository;
import com.techup.minor_cineplex.specification.MovieSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MovieServiceImpl implements MovieService {

    private final MovieRepository movieRepository;

    @Override
    public List<Movie> getAllMovies() {
        return movieRepository.findAll();
    }

    @Override
    public Movie getMovieById(Long id) {
        return movieRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Movie not found"));
    }

    @Override
    public List<Movie> searchMovies(String query) {
        return movieRepository.findByTitleContainingIgnoreCase(query);
    }

    @Override
    public List<Movie> searchMovies(MovieSearchCriteria criteria) {
        return movieRepository.findAll(MovieSpecification.build(criteria));
    }
}

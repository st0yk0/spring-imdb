package org.imdb.movies.services;

import org.imdb.movies.models.MovieModel;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface MovieService {

    MovieModel addMovie(MovieModel model);

    List<MovieModel> getAllMovies();

    MovieModel updateMovie(MovieModel model);

    void deleteMovie(String id);
}

package org.imdb.movies.rest;

import org.imdb.movies.models.MovieModel;
import org.imdb.movies.services.MovieService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/movies")
public class MovieController {

    private final MovieService movieService;
    private final MovieAccessValidator movieAccessValidator;

    public MovieController(MovieService movieService, MovieAccessValidator movieAccessValidator) {
        this.movieService = movieService;
        this.movieAccessValidator = movieAccessValidator;
    }

    @PostMapping
    public MovieModel addMovie(@RequestBody final MovieModel movie) {
        return movieService.addMovie(movie);
    }

    @GetMapping("/all")
    public List<MovieModel> getAllMovies() {
        return movieService.getAllMovies();
    }

    @PutMapping
    public MovieModel updateMovie(@RequestBody final MovieModel movie) {
        return movieService.updateMovie(movie);
    }

    @DeleteMapping("/{id}/{userId}")
    public void deleteMovie(@PathVariable final String id, @PathVariable final String userId) {
        movieAccessValidator.validateUserCanEditMovie(userId, id);

        movieService.deleteMovie(id);
    }
}

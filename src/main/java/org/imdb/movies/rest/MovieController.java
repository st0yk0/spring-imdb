package org.imdb.movies.rest;

import org.imdb.movies.models.MovieModel;
import org.imdb.movies.services.MovieService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/movies")
public class MovieController {

    private final MovieService movieService;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
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

    @DeleteMapping("/{id}")
    public void deleteMovie(@PathVariable final String id) {
        movieService.deleteMovie(id);
    }
}

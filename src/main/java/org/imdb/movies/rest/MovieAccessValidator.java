package org.imdb.movies.rest;

import org.imdb.exceptions.HttpForbiddenException;
import org.imdb.movies.models.MovieModel;
import org.imdb.movies.services.MovieService;
import org.springframework.stereotype.Component;

@Component
public class MovieAccessValidator {

    private final MovieService movieService;

    public MovieAccessValidator(MovieService movieService) {
        this.movieService = movieService;
    }

    void validateUserCanEditMovie(final String userId, final String movieId) {
        if (userId == null || movieId == null) {
            throw new HttpForbiddenException();
        }

        final MovieModel movie = movieService.getById(movieId);

        if (!userId.equals(movie.getUser().getId())) {
            final String message = String
                    .format("Movie with id: %s does not belong to user with id: %s", movieId, userId);
            throw new HttpForbiddenException(message);
        }
    }
}

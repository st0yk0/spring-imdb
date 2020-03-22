package org.imdb.movies.services.impl;

import lombok.extern.log4j.Log4j2;
import org.imdb.actors.entities.Actor;
import org.imdb.actors.entities.ActorRepository;
import org.imdb.actors.models.ActorModel;
import org.imdb.actors.services.converters.ActorConverter;
import org.imdb.exceptions.HttpBadRequestException;
import org.imdb.movies.entities.Movie;
import org.imdb.movies.entities.MovieRepository;
import org.imdb.movies.models.MovieModel;
import org.imdb.movies.services.MovieService;
import org.imdb.movies.services.converters.MovieConverter;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Log4j2
public class MovieServiceImpl implements MovieService {

    private final MovieRepository movieRepository;
    private final MovieConverter movieConverter;
    private final ActorRepository actorRepository;
    private final ActorConverter actorConverter;

    public MovieServiceImpl(MovieRepository movieRepository, MovieConverter movieConverter, ActorRepository actorRepository, ActorConverter actorConverter) {
        this.movieRepository = movieRepository;
        this.movieConverter = movieConverter;
        this.actorRepository = actorRepository;
        this.actorConverter = actorConverter;
    }

    @Override
    public MovieModel addMovie(MovieModel model) {
        log.info("Create movie BEGIN: {}", model);

        final Movie entity = movieConverter.convertToEntity(model);

        log.info("Create actor in movies BEGIN: {}", entity.getActorList());

        List<Actor> actors = entity.getActorList();
        actors.forEach((actor) -> {
            Actor actorFromMovie = actorRepository.save(actor);
            actor.setId(actorFromMovie.getId());
        });

        log.info("Create actor in movies END: {}", entity.getActorList());

        final Movie movie = movieRepository.save(entity);
        final MovieModel created = movieConverter.convertToModel(movie);

        log.info("Create movie END: {}", created);

        return created;
    }

    @Override
    public List<MovieModel> getAllMovies() {
        log.info("Get all movies BEGIN: ");

        final List<Movie> all = movieRepository.findAll();

        final List<MovieModel> movies = movieConverter.convertToModels(all);

        log.info("Get all movies END: ");
        return movies;
    }

    @Override
    public MovieModel updateMovie(MovieModel model) {
        log.info("Update movie BEGIN: {}", model);

        if (!movieRepository.existsById(model.getId())) {
            throw new HttpBadRequestException("Movie entity does not exist for id: " + model.getId());
        }

        final Movie movie = movieConverter.convertToEntity(model);

        final MovieModel updated = movieConverter.convertToModel(movieRepository.save(movie));

        log.info("Update movie END: {}", updated);
        return updated;
    }

    @Override
    public MovieModel getById(String id) {
        log.info("Get movie by id BEGIN: {}", id);

        final Optional<Movie> movieOpt = movieRepository.findById(id);

        MovieModel movie = null;
        if (movieOpt.isPresent()) {
            movie = movieConverter.convertToModel(movieOpt.get());
        }

        log.info("Get movie by id END: {} {}", id, movie);

        return movie;
    }

    @Override
    public void deleteMovie(String id) {
        log.info("Delete movie by id BEGIN: {}", id);

        movieRepository.deleteById(id);

        log.info("Delete movie by id END: {}", id);
    }


}

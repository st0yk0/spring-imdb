package org.imdb.movies.services.converters;

import org.imdb.movies.entities.Movie;
import org.imdb.movies.models.MovieModel;
import org.imdb.users.services.converters.UserConverter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;


@Component
public class MovieConverter {
    private final UserConverter userConverter;

    public MovieConverter(UserConverter userConverter) {
        this.userConverter = userConverter;
    }


    public MovieModel convertToModel(final Movie movie){
        if (movie == null) {
            return null;
        }

        final MovieModel model = new MovieModel();
        model.setId(movie.getId());
        model.setName(movie.getName());
        model.setYear(movie.getYear());
        model.setRating(movie.getRating());
        model.setActorList(movie.getActorList());
        model.setGenre(movie.getGenre());
        model.setPicture(movie.getPicture());
        model.setUser(userConverter.convertToModel(movie.getUser()));
        model.setYoutubeURL(movie.getYoutubeURL());

        return model;
    }

    public Movie convertToEntity(final MovieModel model){
        if (model == null) {
            return null;
        }

        final Movie movie = new Movie();
        movie.setId(model.getId());
        movie.setName(model.getName());
        movie.setYear(model.getYear());
        movie.setRating(model.getRating());
        movie.setActorList(model.getActorList());
        movie.setUser(userConverter.convertToEntity(model.getUser()));
        movie.setGenre(model.getGenre());
        movie.setPicture(model.getPicture());
        movie.setYoutubeURL(model.getYoutubeURL());

        return movie;
    }

    public List<MovieModel> convertToModels(final List<Movie> movies) {
        if (movies == null || movies.isEmpty()) {
            return new ArrayList<>();
        }

        return movies.stream().map(this::convertToModel).collect(toList());
    }
}

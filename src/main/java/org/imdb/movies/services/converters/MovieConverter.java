package org.imdb.movies.services.converters;

import com.mysql.cj.util.StringUtils;
import org.imdb.actors.entities.Actor;
import org.imdb.actors.services.ActorService;
import org.imdb.movies.entities.Movie;
import org.imdb.movies.models.MovieModel;
import org.imdb.users.services.converters.UserConverter;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;


@Component
public class MovieConverter {
    private final UserConverter userConverter;
    private final ActorService actorService;

    public MovieConverter(UserConverter userConverter, ActorService actorService) {
        this.userConverter = userConverter;
        this.actorService = actorService;
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
        model.setGenre(movie.getGenre());
        model.setPicture(movie.getPicture());
        model.setUser(userConverter.convertToModel(movie.getUser()));
        model.setYoutubeURL(movie.getYoutubeURL());
        model.setActorList(toActors(movie.getActorList()));
        return model;
    }

    private String toActors(final List<Actor> actors) {
        if (actors == null || actors.isEmpty()) {
            return null;
        }

        return String.join(",", actors.stream().map(Actor::getName).collect(Collectors.toList()));
    }

    public Movie convertToEntity(final MovieModel model){
        if (model == null) {
            return null;
        }
        System.out.println("model.getActorList() = " + model.getActorList());
        final Movie movie = new Movie();
        movie.setId(model.getId());
        movie.setName(model.getName());
        movie.setYear(model.getYear());
        movie.setRating(model.getRating());
        movie.setUser(userConverter.convertToEntity(model.getUser()));
        movie.setGenre(model.getGenre());
        movie.setPicture(model.getPicture());
        movie.setYoutubeURL(model.getYoutubeURL());
        movie.setActorList(createActorIfMissing(model.getActorList()));
        return movie;
    }

    public List<MovieModel> convertToModels(final List<Movie> movies) {
        if (movies == null || movies.isEmpty()) {
            return new ArrayList<>();
        }

        return movies.stream().map(this::convertToModel).collect(toList());
    }

    private List<Actor> createActorIfMissing(final String actors) {
        if (StringUtils.isNullOrEmpty(actors)) {
            return null;
        }

        final List<String> actorList = new ArrayList<>(Arrays.asList(actors.split(",")));
        final List<Actor> entities = new ArrayList<>();

        actorList.forEach(actor -> {
            final Actor byName = actorService.getByName(actor);
            if (byName != null) {
                entities.add(byName);
            } else {
                entities.add(actorService.create(actor));
            }
        });

        return entities;
    }

}

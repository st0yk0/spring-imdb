package org.imdb.movies.service;

import org.imdb.actors.entities.Actor;
import org.imdb.movies.models.MovieModel;
import org.imdb.movies.services.MovieService;
import org.imdb.users.model.UserModel;
import org.imdb.users.services.UserService;
import org.imdb.users.services.converters.UserConverter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class TestMovieService {

    @Autowired
    private MovieService movieService;

    @Autowired
    private UserService userService;

    @Autowired
    private UserConverter userConverter;

    @Test
    public void createMovie(){
        final MovieModel movie = fillMovieData();

        final MovieModel created = movieService.addMovie(movie);

        assertEquals(movie.getName(), created.getName());
        assertEquals(movie.getYear(), created.getYear());
        assertEquals(movie.getRating(), created.getRating(), 0);
        assertEquals(movie.getActorList(), created.getActorList());
        assertEquals(movie.getGenre(), created.getGenre());
        assertEquals(movie.getUser().getId(), created.getUser().getId());
        assertEquals(movie.getPicture(), created.getPicture());
        assertEquals(movie.getYoutubeURL(), created.getYoutubeURL());


        final MovieModel byId = movieService.getById(created.getId());
        assertNotNull(byId);

        byId.setYear(2010);
        final MovieModel updated = movieService.updateMovie(byId);
        assertEquals(byId.getYear(), updated.getYear());

        final List<MovieModel> allMovies = movieService.getAllMovies();
        assertTrue(allMovies.size() > 0);

        movieService.deleteMovie(updated.getId());

        final MovieModel deleted = movieService.getById(updated.getId());
        assertNull(deleted);

    }

    private MovieModel fillMovieData(){

        final Actor actor = new Actor(null, "Stefan Ryadkov");
        final List<Actor> actorList = new ArrayList<>();
        actorList.add(actor);

        final UserModel user = new UserModel(null, "Joro", "52852", "Joro", "Tornev");
        final UserModel createdUser = userService.registerUser(user);

        System.out.println("createdUser = " + createdUser.toString());

        final MovieModel movie = new MovieModel();

        movie.setName("Toy Story");
        movie.setYear(2019);
        movie.setRating(4.0);
        movie.setActorList(actor.getName());
        movie.setUser(createdUser);
        movie.setGenre("animation");
        movie.setPicture("somePicturePath");
        movie.setYoutubeURL("someYoutubeURL");

        return movie;
    }
}

package org.imdb.movies.entities;

import org.imdb.users.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MovieRepository extends JpaRepository<Movie, String> {
    List<Movie> findAllByUser(User user);
}

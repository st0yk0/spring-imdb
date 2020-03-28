package org.imdb.movies.entities;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.imdb.actors.entities.Actor;
import org.imdb.users.entities.User;

import javax.persistence.*;

import java.awt.image.BufferedImage;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import static org.imdb.constants.Constants.UUID_SIZE;

@Data
@Entity
@Table(name = "movies")
public class Movie {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(length = UUID_SIZE)
    private String id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private int year;

    @Column(nullable = false)
    private double rating;


    @Column(nullable = false)
    private String genre;

    @Column
    private String picture;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false,
            foreignKey = @ForeignKey(name = "fk_movies_users"))
    private User user;

    @Column
    private String youtubeURL;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "movie_actors",
            joinColumns = {@JoinColumn(name = "movie_id", nullable = false,
                    foreignKey = @ForeignKey(name = "fk_movie_actors_movie"))},
            inverseJoinColumns = {@JoinColumn(name = "movie_actor_id", nullable = false,
                    foreignKey = @ForeignKey(name = "fk_movie_actors_actors"))})
    private List<Actor> actorList;
}

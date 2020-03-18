package org.imdb.movies.entities;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.imdb.actors.entities.Actor;

import javax.persistence.*;

import java.awt.image.BufferedImage;
import java.time.LocalDate;
import java.util.List;

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

    @ElementCollection
    @Column(nullable = false )
    private List<Actor> actorList;

    @Column(nullable = false)
    private String genre;

    @Column
    @Lob
    @Basic(fetch = FetchType.LAZY)
    private byte[] picture;

    @Column
    private String youtubeURL;
}

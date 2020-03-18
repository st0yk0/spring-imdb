package org.imdb.movies.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.imdb.actors.entities.Actor;

import java.awt.image.BufferedImage;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MovieModel {

    private String id;
    private String name;
    private int year;
    private double rating;
    private List<Actor> actorList;
    private String genre;
    //private byte[] picture;
    private String youtubeURL;
}
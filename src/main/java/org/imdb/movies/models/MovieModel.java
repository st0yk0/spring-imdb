package org.imdb.movies.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.imdb.users.model.UserModel;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class MovieModel {

    private String id;
    private String name;
    private int year;
    private double rating;
    private String actorList;
    private String genre;
    private String picture;
    private UserModel user;
    private String youtubeURL;
}

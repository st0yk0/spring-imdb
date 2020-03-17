package org.imdb.actors.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ActorModel {

    private String id;
    private String firstName;
    private String lastName;
}

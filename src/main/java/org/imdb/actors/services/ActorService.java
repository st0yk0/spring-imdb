package org.imdb.actors.services;

import org.imdb.actors.models.ActorModel;

import java.util.List;

public interface ActorService {

    ActorModel addActor(ActorModel model);

    List<ActorModel> getAllActors();

    ActorModel updateActor(ActorModel model);

    void deleteActor(String id);
}

package org.imdb.actors.services.converters;

import org.imdb.actors.entities.Actor;
import org.imdb.actors.models.ActorModel;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Component
public class ActorConverter {

    public ActorModel convertToModel(final Actor actor){
        if (actor == null) {
            return null;
        }

        final ActorModel model = new ActorModel();
        model.setId(actor.getId());
        model.setName(actor.getName());

        return model;
    }

    public Actor convertToEntity(final String name) {
        if (name == null) {
            return null;
        }

        return new Actor(null, name);
    }

    public Actor convertToEntity(final ActorModel model){
        if(model == null){
            return null;
        }

        final Actor actor = new Actor();
        actor.setId(model.getId());
        actor.setName(model.getName());

        return actor;
    }

    public List<ActorModel> convertToModels(final List<Actor> actors) {
        if (actors == null || actors.isEmpty()) {
            return new ArrayList<>();
        }

        return actors.stream().map(this::convertToModel).collect(toList());
    }
}

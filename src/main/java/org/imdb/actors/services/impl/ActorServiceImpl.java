package org.imdb.actors.services.impl;

import lombok.extern.log4j.Log4j2;
import org.imdb.actors.entities.Actor;
import org.imdb.actors.entities.ActorRepository;
import org.imdb.actors.models.ActorModel;
import org.imdb.actors.services.ActorService;
import org.imdb.actors.services.converters.ActorConverter;
import org.imdb.exceptions.HttpBadRequestException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Log4j2
public class ActorServiceImpl implements ActorService {

    private final ActorRepository actorRepository;
    private final ActorConverter actorConverter;

    public ActorServiceImpl(ActorRepository actorRepository, ActorConverter actorConverter) {
        this.actorRepository = actorRepository;
        this.actorConverter = actorConverter;
    }


    @Override
    public ActorModel addActor(final ActorModel model) {
        log.info("Create actor BEGIN: {}", model);

        final Actor entity = actorConverter.convertToEntity(model);
        final Actor actor = actorRepository.save(entity);
        final ActorModel created = actorConverter.convertToModel(actor);

        log.info("Create actor END: {}", created);

        return created;
    }

    @Override
    public List<ActorModel> getAllActors() {
        log.info("Get all actors BEGIN: ");

        final List<Actor> all = actorRepository.findAll();

        final List<ActorModel> actors = actorConverter.convertToModels(all);

        log.info("Get all cars END: ");
        return actors;
    }

    @Override
    public ActorModel updateActor(final ActorModel model) {
        log.info("Update actor BEGIN: {}", model);

        if (!actorRepository.existsById(model.getId())) {
            throw new HttpBadRequestException("Car entity does not exist for id: " + model.getId());
        }

        final Actor actor = actorConverter.convertToEntity(model);

        final ActorModel updated = actorConverter.convertToModel(actorRepository.save(actor));

        log.info("Update car END: {}", updated);
        return updated;
    }

    @Override
    public void deleteActor(final String id) {
        log.info("Delete actor by id BEGIN: {}", id);

        actorRepository.deleteById(id);

        log.info("Delete actor by id END: {}", id);

    }
    @Override
    public Actor create(final String name) {
        log.info("Create actor string BEGIN: {}", name);

        if (name == null) {
            return null;
        }

        final Actor actorEntity = actorConverter.convertToEntity(name);
        final Actor created = actorRepository.save(actorEntity);

        log.info("Create actor string END: {}", created);

        return created;
    }

    @Override
    public Actor getByName(String name) {
        log.info("Get actor by name BEGIN: {}", name);

        if (name == null) {
            return null;
        }

        final Optional<Actor> actorOpt = actorRepository.findByName(name);
        final Actor actor = actorOpt.orElse(null);

        log.info("Get actor by name BEGIN: {}", actor);

        return actor;
    }


}

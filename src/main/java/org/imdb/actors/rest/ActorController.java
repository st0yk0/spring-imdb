package org.imdb.actors.rest;

import org.imdb.actors.models.ActorModel;
import org.imdb.actors.services.ActorService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/actors")
public class ActorController {

    private final ActorService actorService;


    public ActorController(ActorService actorService) {
        this.actorService = actorService;
    }

    @PostMapping
    public ActorModel addActor(@RequestBody final ActorModel actor) {
        return actorService.addActor(actor);
    }

    @GetMapping("/all")
    public List<ActorModel> getAllCars() {
        return actorService.getAllActors();
    }

    @PutMapping
    public ActorModel updateActor(@RequestBody final ActorModel actor) {

        return actorService.updateActor(actor);
    }

    @DeleteMapping("/{id}")
    public void deleteActor(@PathVariable final String id) {
        actorService.deleteActor(id);
    }
}

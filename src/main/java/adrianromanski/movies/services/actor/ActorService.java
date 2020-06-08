package adrianromanski.movies.services.actor;

import adrianromanski.movies.model.ActorDTO;

import java.util.List;

public interface ActorService {

    //GET
    List<ActorDTO> getAllActors();

    //POST
    ActorDTO createActor(ActorDTO actorDTO);

    //PUT
    ActorDTO updateActor(Long id, ActorDTO actorDTO);

    //DELETE
    void deleteActorByID(Long id);
}

package adrianromanski.movies.services.actor;

import adrianromanski.movies.model.person.ActorDTO;
import adrianromanski.movies.model.award.ActorAwardDTO;

import java.util.List;

public interface ActorService {

    //GET
    List<ActorDTO> getAllActors();

    //POST
    ActorDTO createActor(ActorDTO actorDTO);

    ActorAwardDTO addAward(Long actorID, ActorAwardDTO awardDTO);

    //PUT
    ActorDTO updateActor(Long id, ActorDTO actorDTO);

    ActorAwardDTO updateAward(Long actorID, Long awardID, ActorAwardDTO awardDTO);

    //DELETE
    void deleteActorByID(Long id);

    void deleteAward(Long actorID, Long awardID);
}

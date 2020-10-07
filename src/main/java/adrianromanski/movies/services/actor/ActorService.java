package adrianromanski.movies.services.actor;

import adrianromanski.movies.domain.person.Actor;
import adrianromanski.movies.model.award.ActorAwardDTO;
import adrianromanski.movies.model.base_entity.MovieDTO;
import adrianromanski.movies.model.person.ActorDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ActorService {

    //GET
    List<ActorDTO> getAllActors();

    ActorDTO getActorByID(Long id);

//    List<ActorDTO> getActorByRating();

    List<MovieDTO> getAllMoviesForActor(Long id);

    Page<Actor> getActorsPaged(Pageable pageable);

    //POST
    ActorDTO createActor(ActorDTO actorDTO);

    ActorDTO addMovie(Long actorID, Long movieID);

    ActorAwardDTO addAward(Long actorID, ActorAwardDTO awardDTO);

    //PUT
    ActorDTO updateActor(Long id, ActorDTO actorDTO);

    ActorAwardDTO updateAward(Long actorID, Long awardID, ActorAwardDTO awardDTO);

    //DELETE
    void deleteActorByID(Long id);

    void deleteMovie(Long actorID, Long movieID);

    void deleteAward(Long actorID, Long awardID);
}

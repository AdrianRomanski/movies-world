package adrianromanski.movies.services.actor;

import adrianromanski.movies.aspects.creation_log.LogCreation;
import adrianromanski.movies.aspects.delete_log.LogDelete;
import adrianromanski.movies.aspects.paging_log.LogPaging;
import adrianromanski.movies.aspects.update_log.LogUpdate;
import adrianromanski.movies.domain.award.ActorAward;
import adrianromanski.movies.domain.award.Award;
import adrianromanski.movies.domain.person.Actor;
import adrianromanski.movies.exceptions.ResourceNotFoundException;
import adrianromanski.movies.mapper.award.ActorAwardMapper;
import adrianromanski.movies.mapper.person.ActorMapper;
import adrianromanski.movies.model.award.ActorAwardDTO;
import adrianromanski.movies.model.base_entity.MovieDTO;
import adrianromanski.movies.model.person.ActorDTO;
import adrianromanski.movies.repositories.award.AwardRepository;
import adrianromanski.movies.repositories.pages.ActorPageRepository;
import adrianromanski.movies.repositories.person.ActorRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.stream.Collectors.toList;


@Service
@AllArgsConstructor
public class ActorServiceImpl implements ActorService {

    private final ActorRepository actorRepository;
    private final AwardRepository awardRepository;
    private final ActorPageRepository actorPageRepository;
    private final ActorMapper actorMapper;
    private final ActorAwardMapper awardMapper;


    /**
     * @return All actors from Database
     */
    @Override
    public List<ActorDTO> getAllActors() {
        return actorRepository.findAll()
                .stream()
                .map(actorMapper::actorToActorDTO)
                .collect(toList());
    }

    /**
     * @return  Actor with matching id
     */
    @Override
    public ActorDTO getActorByID(Long id) {
        return actorRepository.findById(id)
                .map(actorMapper::actorToActorDTO)
                .orElseThrow(() -> new ResourceNotFoundException(id, Actor.class));
    }

    /**
     * @return All Movies For the Actor with matching id
     */
    @Override
    public List<MovieDTO> getAllMoviesForActor(Long id) {
        ActorDTO actorDTO = actorRepository.findById(id)
                .map(actorMapper::actorToActorDTO)
                .orElseThrow(() -> new ResourceNotFoundException(id, Actor.class));
        return actorDTO.getMoviesDTO();
    }

    /**
     * @return All Actors Paged
     */
    @Override
    @LogPaging
    public Page<Actor> getActorsPaged(Pageable pageable) {
        return actorPageRepository.findAll(pageable);
    }



    /**
     * @param actorDTO object for saving
     * @return Actor if successfully saved
     */
    @Override
    @LogCreation
    public ActorDTO createActor(ActorDTO actorDTO) {
        Actor actor = actorMapper.actorDTOToActor(actorDTO);
        actorRepository.save(actor);
        return actorMapper.actorToActorDTO(actor);
    }


    /**
     * @param actorID of the Actor to add Award
     * @param awardDTO object for adding
     * @return Award if successfully added Award to Actor
     * @throws ResourceNotFoundException if Actor not found
     */
    @Override
    public ActorAwardDTO addAward(Long actorID, ActorAwardDTO awardDTO) {
        Actor actor = actorRepository.findById(actorID)
                .orElseThrow(() -> new ResourceNotFoundException(actorID, Actor.class));
        ActorAward award = awardMapper.awardDTOToAward(awardDTO);
        actor.getAwards().add(award);
        award.setActor(actor);
        actorRepository.save(actor);
        awardRepository.save(award);
        return awardMapper.awardToAwardDTO(award);
    }


    /**
     * @param id of the Actor to update
     * @param actorDTO object with updated fields
     * @return ActorDTO if successfully updated
     * @throws ResourceNotFoundException if not found
     */
    @Override
    @LogUpdate
    public ActorDTO updateActor(Long id, ActorDTO actorDTO) {
        Actor actor = actorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(id, Actor.class));
        Actor updatedActor = actorMapper.actorDTOToActor(actorDTO);
        actor.getAwards()
                .forEach(award -> updatedActor.getAwards().add(award));
        actor.getMovies()
                .forEach(movie -> updatedActor.getMovies().add(movie));
        updatedActor.setId(id);
        actorRepository.save(updatedActor);
        return actorMapper.actorToActorDTO(updatedActor);
    }


    /**
     * @param actorID of The Actor we want to update Award
     * @param awardID of the Award we want to update
     * @param awardDTO Object with updated fields
     * @return Award if successfully updated
     * @throws ResourceNotFoundException if Actor or Award not found
     */
    @Override
    public ActorAwardDTO updateAward(Long actorID, Long awardID, ActorAwardDTO awardDTO) {
        Actor actor = actorRepository.findById(actorID)
                .orElseThrow(() -> new ResourceNotFoundException(actorID, Actor.class));
        ActorAward award = actor.getAwardOptional(awardID)
                .orElseThrow(() -> new ResourceNotFoundException(awardID, Award.class));
        actor.getAwards().remove(award);
        ActorAward updatedAward = awardMapper.awardDTOToAward(awardDTO);
        updatedAward.setId(awardID);
        actor.getAwards().add(updatedAward);
        awardRepository.save(updatedAward);
        actorRepository.save(actor);
        return awardMapper.awardToAwardDTO(updatedAward);
    }


    /**
     * @param id of the Actor to delete
     * @throws ResourceNotFoundException if not found
     */
    @Override
    @LogDelete
    public void deleteActorByID(Long id) {
        actorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(id, Actor.class));
        actorRepository.deleteById(id);
    }


    /**
     * @param actorID of The Actor we want to delete Award
     * @param awardID of the Award we want to delete
     * @throws ResourceNotFoundException if Actor or Award not found
     */
    @Override
    @LogDelete
    public void deleteAward(Long actorID, Long awardID) {
        Actor actor = actorRepository.findById(actorID)
                .orElseThrow(() -> new ResourceNotFoundException(actorID, Actor.class));
        ActorAward award = actor.getAwardOptional(awardID)
                .orElseThrow(() -> new ResourceNotFoundException(awardID, Award.class));
        actor.getAwards().remove(award);
        actorRepository.save(actor);
        awardRepository.deleteById(awardID);
    }
}

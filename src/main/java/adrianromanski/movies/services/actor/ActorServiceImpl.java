package adrianromanski.movies.services.actor;

import adrianromanski.movies.domain.Actor;
import adrianromanski.movies.exceptions.ResourceNotFoundException;
import adrianromanski.movies.mapper.ActorMapper;
import adrianromanski.movies.model.ActorDTO;
import adrianromanski.movies.repositories.ActorRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.stream.Collectors.*;

@Slf4j
@Service
public class ActorServiceImpl implements ActorService {

    private final ActorRepository actorRepository;
    private final ActorMapper actorMapper;

    public ActorServiceImpl(ActorRepository actorRepository, ActorMapper actorMapper) {
        this.actorRepository = actorRepository;
        this.actorMapper = actorMapper;
    }

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
     * @param actorDTO object for saving
     * @return Actor if successfully saved
     */
    @Override
    public ActorDTO createActor(ActorDTO actorDTO) {
        Actor actor = actorMapper.actorDTOToActor(actorDTO);
        actorRepository.save(actor);
        log.info("Actor with id: " + actor.getId() + " successfully saved");
        return actorMapper.actorToActorDTO(actor);
    }


    /**
     * @param id of the Actor to update
     * @param actorDTO object for updating
     * @return ActorDTO if successfully updated
     * @throws ResourceNotFoundException if not found
     */
    @Override
    public ActorDTO updateActor(Long id, ActorDTO actorDTO) {
        Actor actor = actorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(id, Actor.class));
        Actor updatedActor = actorMapper.actorDTOToActor(actorDTO);
        updatedActor.setId(id);
        actorRepository.save(updatedActor);
        log.info("Actor with id: " + actor.getId() + " successfully updated");
        return actorMapper.actorToActorDTO(updatedActor);
    }


    /**
     * @param id of the Actor to delete
     * @throws ResourceNotFoundException if not found
     */
    @Override
    public void deleteActorByID(Long id) {
        actorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(id, Actor.class));
        actorRepository.deleteById(id);
    }
}

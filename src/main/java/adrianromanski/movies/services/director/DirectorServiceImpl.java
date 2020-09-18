package adrianromanski.movies.services.director;

import adrianromanski.movies.domain.award.DirectorAward;
import adrianromanski.movies.domain.person.Director;
import adrianromanski.movies.exceptions.ResourceNotFoundException;
import adrianromanski.movies.mapper.award.DirectorAwardMapper;
import adrianromanski.movies.mapper.person.DirectorMapper;
import adrianromanski.movies.model.award.DirectorAwardDTO;
import adrianromanski.movies.model.person.DirectorDTO;
import adrianromanski.movies.repositories.award.AwardRepository;
import adrianromanski.movies.repositories.person.DirectorRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class DirectorServiceImpl implements DirectorService {

    private final DirectorRepository directorRepository;
    private final AwardRepository awardRepository;
    private final DirectorMapper directorMapper;
    private final DirectorAwardMapper awardMapper;


    /**
     * @param id of the Director we are Looking for
     * @throws ResourceNotFoundException if not found
     * @return DirectorDTO
     */
    @Override
    public DirectorDTO getDirectorByID(Long id) {
        return directorRepository.findById(id)
                .map(directorMapper::directorToDirectorDTO)
                .orElseThrow(() -> new ResourceNotFoundException(id, Director.class));
    }


    /**
     * @param firstName of the Director
     * @param lastName of the Director
     * @return DirectorDTO
     */
    @Override
    public DirectorDTO getDirectorByName(String firstName, String lastName) {
        return directorRepository.getDirectorByFirstNameAndAndLastName(firstName, lastName)
                .map(directorMapper::directorToDirectorDTO)
                .orElseThrow(() -> new ResourceNotFoundException(firstName, lastName, Director.class));
    }


    /**
     * @param directorDTO body
     * @return DirectorDTO
     */
    @Override
    public DirectorDTO createNewDirector(DirectorDTO directorDTO) {
        Director director = directorMapper.directorDTOToDirector(directorDTO);
        directorRepository.save(director);
        return directorMapper.directorToDirectorDTO(director);
    }


    /**
     * @param id of The Director we want to add Award
     * @param awardDTO body
     * @return DirectorAwardDTO if successfully added
     * @throws ResourceNotFoundException if not found
     */
    @Override
    public DirectorAwardDTO addAward(Long id, DirectorAwardDTO awardDTO) {
        Director director = directorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(id, Director.class));
        DirectorAward award = awardMapper.awardDTOToAward(awardDTO);
        director.getAwards().add(award);
        directorRepository.save(director);
        awardRepository.save(award);
        return awardMapper.awardToAwardDTO(award);
    }


    /**
     * @param id of the Director we want to update
     * @param directorDTO body
     * @throws ResourceNotFoundException if not found
     * @return DirectorDTO
     */
    @Override
    public DirectorDTO updateDirector(Long id, DirectorDTO directorDTO) {
        Director  director = directorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(id, Director.class));
        Director updatedDirector = directorMapper.directorDTOToDirector(directorDTO);
        director.getMovies()
                .forEach(m -> updatedDirector.getMovies().add(m));
        director.getAwards()
                .forEach(a -> updatedDirector.getAwards().add(a));
        directorRepository.save(updatedDirector);
        return directorMapper.directorToDirectorDTO(updatedDirector);
    }


    /**
     * @param directorID of the Director we want to update Award
     * @param awardID of the Award we want to update
     * @param awardDTO body
     * @throws ResourceNotFoundException if not found
     * @return DirectorAwardDTO if successfully updated
     */
    @Override
    public DirectorAwardDTO updateAward(Long directorID, Long awardID, DirectorAwardDTO awardDTO) {
        Director  director = directorRepository.findById(directorID)
                .orElseThrow(() -> new ResourceNotFoundException(directorID, Director.class));
        DirectorAward award = director.getAwardOptional(awardID)
                .orElseThrow(() -> new ResourceNotFoundException(awardID, DirectorAward.class));
        director.getAwards().remove(award);
        DirectorAward updatedAward = awardMapper.awardDTOToAward(awardDTO);
        updatedAward.setId(awardID);
        director.getAwards().add(updatedAward);
        updatedAward.setDirector(director);
        directorRepository.save(director);
        awardRepository.save(updatedAward);
        return awardMapper.awardToAwardDTO(updatedAward);
    }


    /**
     * @param id of the Director we wants to deleted
     * @throws ResourceNotFoundException if not found
     */
    @Override
    public void deleteDirectorByID(Long id) {
        directorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(id, Director.class));
        directorRepository.deleteById(id);
    }


    /**
     * @param directorID of the Director we want to delete Award
     * @param awardID of the Award we want to delete
     * @throws ResourceNotFoundException if not found
     */
    @Override
    public void deleteAwardByID(Long directorID, Long awardID) {
        Director  director = directorRepository.findById(directorID)
                .orElseThrow(() -> new ResourceNotFoundException(directorID, Director.class));
        DirectorAward award = director.getAwardOptional(awardID)
                .orElseThrow(() -> new ResourceNotFoundException(awardID, DirectorAward.class));
        director.getAwards().remove(award);
        directorRepository.save(director);
        awardRepository.deleteById(awardID);
    }
}

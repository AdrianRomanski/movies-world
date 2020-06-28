package adrianromanski.movies.services.director;

import adrianromanski.movies.domain.award.DirectorAward;
import adrianromanski.movies.domain.person.Director;
import adrianromanski.movies.exceptions.ResourceNotFoundException;
import adrianromanski.movies.jms.JmsTextMessageService;
import adrianromanski.movies.mapper.award.DirectorAwardMapper;
import adrianromanski.movies.mapper.person.DirectorMapper;
import adrianromanski.movies.model.award.DirectorAwardDTO;
import adrianromanski.movies.model.person.DirectorDTO;
import adrianromanski.movies.repositories.AwardRepository;
import adrianromanski.movies.repositories.DirectorRepository;
import org.springframework.stereotype.Service;

@Service
public class DirectorServiceImpl implements DirectorService {

    private final DirectorRepository directorRepository;
    private final AwardRepository awardRepository;
    private final DirectorMapper directorMapper;
    private final DirectorAwardMapper awardMapper;
    private final JmsTextMessageService jmsTextMessageService;


    public DirectorServiceImpl(DirectorRepository directorRepository, AwardRepository awardRepository,
                               DirectorMapper directorMapper, DirectorAwardMapper awardMapper,
                               JmsTextMessageService jmsTextMessageService) {
        this.directorRepository = directorRepository;
        this.awardRepository = awardRepository;
        this.directorMapper = directorMapper;
        this.awardMapper = awardMapper;
        this.jmsTextMessageService = jmsTextMessageService;
    }

    /**
     * @param id of the Director we are Looking for
     * @throws ResourceNotFoundException if not found
     * @return DirectorDTO
     */
    @Override
    public DirectorDTO getDirectorByID(Long id) {
        jmsTextMessageService.sendTextMessage("Finding Director with id: " + id);
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
        jmsTextMessageService.sendTextMessage("Finding Director: " + firstName + " "  + lastName);
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
        jmsTextMessageService.sendTextMessage("Creating new Director: " + directorDTO.getFirstName() + " "  + directorDTO.getLastName());
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
        jmsTextMessageService.sendTextMessage("Adding award to Director with id: " + id);
        Director director = directorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(id, Director.class));
        DirectorAward award = awardMapper.awardDTOToAward(awardDTO);
        director.getAwards().add(award);
        directorRepository.save(director);
        awardRepository.save(award);
        jmsTextMessageService.sendTextMessage("Successfully added award to Director with id: " + id);
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
        jmsTextMessageService.sendTextMessage("Updating Director with id: " + id);
        Director  director = directorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(id, Director.class));
        Director updatedDirector = directorMapper.directorDTOToDirector(directorDTO);
        director.getMovies()
                .forEach(m -> updatedDirector.getMovies().add(m));
        director.getAwards()
                .forEach(a -> updatedDirector.getAwards().add(a));
        directorRepository.save(updatedDirector);
        jmsTextMessageService.sendTextMessage("Director with id: " + id + " successfully updated");
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
        jmsTextMessageService.sendTextMessage("Updating Award with id: " + awardID);
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
        jmsTextMessageService.sendTextMessage("Successfully updated Award with id: " + awardID);
        return awardMapper.awardToAwardDTO(updatedAward);
    }


    /**
     * @param id of the Director we wants to deleted
     * @throws ResourceNotFoundException if not found
     */
    @Override
    public void deleteDirectorByID(Long id) {
        jmsTextMessageService.sendTextMessage("Deleting Director with id: " + id);
        directorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(id, Director.class));
        directorRepository.deleteById(id);
        jmsTextMessageService.sendTextMessage("Director with id: " + id + " successfully deleted");
    }


    /**
     * @param directorID of the Director we want to delete Award
     * @param awardID of the Award we want to delete
     * @throws ResourceNotFoundException if not found
     */
    @Override
    public void deleteAwardByID(Long directorID, Long awardID) {
        jmsTextMessageService.sendTextMessage("Deleting Award with id: " + awardID);
        Director  director = directorRepository.findById(directorID)
                .orElseThrow(() -> new ResourceNotFoundException(directorID, Director.class));
        DirectorAward award = director.getAwardOptional(awardID)
                .orElseThrow(() -> new ResourceNotFoundException(awardID, DirectorAward.class));
        director.getAwards().remove(award);
        directorRepository.save(director);
        awardRepository.deleteById(awardID);
        jmsTextMessageService.sendTextMessage("Successfully Deleted Award with id: " + awardID);
    }
}

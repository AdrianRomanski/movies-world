package adrianromanski.movies.services.director;

import adrianromanski.movies.domain.person.Director;
import adrianromanski.movies.exceptions.ResourceNotFoundException;
import adrianromanski.movies.jms.JmsTextMessageService;
import adrianromanski.movies.mapper.person.DirectorMapper;
import adrianromanski.movies.model.person.DirectorDTO;
import adrianromanski.movies.repositories.DirectorRepository;
import org.springframework.stereotype.Service;

@Service
public class DirectorServiceImpl implements DirectorService {

    private final DirectorRepository directorRepository;
    private final DirectorMapper directorMapper;
    private final JmsTextMessageService jmsTextMessageService;

    public DirectorServiceImpl(DirectorRepository directorRepository, DirectorMapper directorMapper,
                               JmsTextMessageService jmsTextMessageService) {
        this.directorRepository = directorRepository;
        this.directorMapper = directorMapper;
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
        directorRepository.save(updatedDirector);
        jmsTextMessageService.sendTextMessage("Director with id: " + id + " successfully updated");
        return directorMapper.directorToDirectorDTO(updatedDirector);
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
}

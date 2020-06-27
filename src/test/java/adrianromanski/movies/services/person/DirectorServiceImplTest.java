package adrianromanski.movies.services.person;

import adrianromanski.movies.domain.base_entity.Movie;
import adrianromanski.movies.domain.person.Director;
import adrianromanski.movies.exceptions.ResourceNotFoundException;
import adrianromanski.movies.jms.JmsTextMessageService;
import adrianromanski.movies.mapper.person.DirectorMapper;
import adrianromanski.movies.mapper.person.DirectorMapperImpl;
import adrianromanski.movies.model.person.DirectorDTO;
import adrianromanski.movies.repositories.DirectorRepository;
import adrianromanski.movies.services.director.DirectorService;
import adrianromanski.movies.services.director.DirectorServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class DirectorServiceImplTest {

    public static final String FIRST_NAME = "Steven";
    public static final String LAST_NAME = "Spielberg";
    public static final String MALE = "Male";
    public static final String STAR_WARS = "Star Wars";
    public static final String INDIANA_JONES = "Indiana Jones";
    @Mock
    DirectorRepository directorRepository;

    @Mock
    JmsTextMessageService jms;

    DirectorService directorService;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);

        DirectorMapper mapper = new DirectorMapperImpl();

        directorService = new DirectorServiceImpl(directorRepository, mapper, jms);
    }

    private Movie getStarWars() { return Movie.builder().name(STAR_WARS).build();}
    private Movie getIndianaJones(){ return Movie.builder().name(INDIANA_JONES).build();}
    private Director getDirector() { return Director.builder().firstName(FIRST_NAME).lastName(LAST_NAME).gender(MALE)
            .movies(Arrays.asList(getStarWars(), getIndianaJones())).build(); }
    private DirectorDTO getDirectorDTO() { return DirectorDTO.builder().firstName(FIRST_NAME).lastName(LAST_NAME).gender(MALE).build(); }


    @DisplayName("Happy Path, method = getDirectorByID")
    @Test
    void getDirectorByIDHappyPath() {
        Director director = getDirector();

        when(directorRepository.findById(anyLong())).thenReturn(Optional.of(director));

        DirectorDTO returnDTO = directorService.getDirectorByID(1L);

        Assertions.assertEquals(returnDTO.getFirstName(), FIRST_NAME);
        Assertions.assertEquals(returnDTO.getLastName(), LAST_NAME);
        Assertions.assertEquals(returnDTO.getGender(), MALE);
    }


    @DisplayName("UnHappy Path, method = getDirectorByID")
    @Test
    void getDirectorByIDUnHappyPath() {
        Throwable ex = catchThrowable(() -> directorService.getDirectorByID(521L));

        assertThat(ex).isInstanceOf(ResourceNotFoundException.class);
    }


    @DisplayName("Happy Path, method = getDirectorByName")
    @Test
    void getDirectorByNameHappyPath() {
        Director director = getDirector();

        when(directorRepository.getDirectorByFirstNameAndAndLastName(anyString(), anyString())).thenReturn(Optional.of(director));

        DirectorDTO returnDTO = directorService.getDirectorByName(FIRST_NAME, LAST_NAME);

        Assertions.assertEquals(returnDTO.getFirstName(), FIRST_NAME);
        Assertions.assertEquals(returnDTO.getLastName(), LAST_NAME);
        Assertions.assertEquals(returnDTO.getGender(), MALE);
    }


    @DisplayName("UnHappy Path, method = getDirectorByName")
    @Test
    void getDirectorByNameUnHappyPath() {
        Throwable ex = catchThrowable(() -> directorService.getDirectorByName("Bruce", "Willis"));

        assertThat(ex).isInstanceOf(ResourceNotFoundException.class);
    }


    @DisplayName("Happy Path, method = createNewDirector")
    @Test
    void createNewDirector() {
        DirectorDTO directorDTO = getDirectorDTO();

        DirectorDTO returnDTO = directorService.createNewDirector(directorDTO);

        Assertions.assertEquals(returnDTO.getFirstName(), FIRST_NAME);
        Assertions.assertEquals(returnDTO.getLastName(), LAST_NAME);
        Assertions.assertEquals(returnDTO.getGender(), MALE);
    }


    @DisplayName("Happy Path, method = updateDirector")
    @Test
    void updateDirectorHappyPath() {
        DirectorDTO directorDTO = getDirectorDTO();
        directorDTO.setFirstName("Checking");
        Director director = getDirector();

        when(directorRepository.findById(anyLong())).thenReturn(Optional.of(director));

        DirectorDTO returnDTO = directorService.updateDirector(1L, directorDTO);

        Assertions.assertEquals(returnDTO.getFirstName(), "Checking");
    }


    @DisplayName("UnHappy Path, method = updateDirector")
    @Test
    void updateDirectorUnHappyPath() {
        DirectorDTO directorDTO = new DirectorDTO();
        Throwable ex = catchThrowable(() -> directorService.updateDirector(1L, directorDTO));

        assertThat(ex).isInstanceOf(ResourceNotFoundException.class);
    }


    @DisplayName("UnHappy Path, method = deleteDirectorByID")
    @Test
    void deleteDirectorByIDHappyPath() {
        Director director = getDirector();

        when(directorRepository.findById(anyLong())).thenReturn(Optional.of(director));

        directorService.deleteDirectorByID(1L);

        verify(directorRepository, times(1)).deleteById(1L);
    }


    @DisplayName("UnHappy Path, method = deleteDirectorByID")
    @Test
    void deleteDirectorByIDUnHappyPath() {
        Throwable ex = catchThrowable(() -> directorService.deleteDirectorByID(1L));

        assertThat(ex).isInstanceOf(ResourceNotFoundException.class);
    }
}
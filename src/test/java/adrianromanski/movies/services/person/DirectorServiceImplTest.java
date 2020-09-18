package adrianromanski.movies.services.person;

import adrianromanski.movies.domain.award.DirectorAward;
import adrianromanski.movies.domain.base_entity.Movie;
import adrianromanski.movies.domain.person.Director;
import adrianromanski.movies.exceptions.ResourceNotFoundException;
import adrianromanski.movies.mapper.award.DirectorAwardMapper;
import adrianromanski.movies.mapper.award.DirectorAwardMapperImpl;
import adrianromanski.movies.mapper.person.DirectorMapper;
import adrianromanski.movies.mapper.person.DirectorMapperImpl;
import adrianromanski.movies.model.award.DirectorAwardDTO;
import adrianromanski.movies.model.person.DirectorDTO;
import adrianromanski.movies.repositories.award.AwardRepository;
import adrianromanski.movies.repositories.person.DirectorRepository;
import adrianromanski.movies.services.director.DirectorService;
import adrianromanski.movies.services.director.DirectorServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class DirectorServiceImplTest {

    public static final String FIRST_NAME = "Steven";
    public static final String LAST_NAME = "Spielberg";
    public static final String MALE = "Male";
    public static final String STAR_WARS = "Star Wars";
    public static final String INDIANA_JONES = "Indiana Jones";
    public static final String GENDER = "Male";
    public static final String CATEGORY = "Best Idea";
    public static final LocalDate DATE = LocalDate.now();
    public static final String COUNTRY = "Poland";

    @Mock
    DirectorRepository directorRepository;

    @Mock
    AwardRepository awardRepository;



    DirectorService directorService;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);

        DirectorMapper directorMapper = new DirectorMapperImpl();
        DirectorAwardMapper awardMapper = new DirectorAwardMapperImpl();

        directorService = new DirectorServiceImpl(directorRepository, awardRepository,
                                                    directorMapper, awardMapper);
    }

    private Movie getStarWars() { return Movie.builder().name(STAR_WARS).build();}
    private Movie getIndianaJones(){ return Movie.builder().name(INDIANA_JONES).build();}

    private Director getDirector() { return Director.builder().firstName(FIRST_NAME).lastName(LAST_NAME).gender(MALE)
            .movies(Arrays.asList(getStarWars(), getIndianaJones())).build(); }
    private DirectorDTO getDirectorDTO() { return DirectorDTO.builder().firstName(FIRST_NAME).lastName(LAST_NAME).gender(MALE).build(); }

    private DirectorAwardDTO getAwardDTO() { return DirectorAwardDTO.builder().awardCategory(CATEGORY).date(DATE).country(COUNTRY).build(); }
    private DirectorAward getAward() { return DirectorAward.builder().awardCategory(CATEGORY).date(DATE).country(COUNTRY).id(1L).build(); }


    @DisplayName("Happy Path, method = getDirectorByID")
    @Test
    void getDirectorByIDHappyPath() {
        Director director = getDirector();

        when(directorRepository.findById(anyLong())).thenReturn(Optional.of(director));

        DirectorDTO returnDTO = directorService.getDirectorByID(1L);

        assertEquals(returnDTO.getFirstName(), FIRST_NAME);
        assertEquals(returnDTO.getLastName(), LAST_NAME);
        assertEquals(returnDTO.getGender(), MALE);
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

        assertEquals(returnDTO.getFirstName(), FIRST_NAME);
        assertEquals(returnDTO.getLastName(), LAST_NAME);
        assertEquals(returnDTO.getGender(), MALE);
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

        assertEquals(returnDTO.getFirstName(), FIRST_NAME);
        assertEquals(returnDTO.getLastName(), LAST_NAME);
        assertEquals(returnDTO.getGender(), MALE);
    }


    @DisplayName("Happy Path, method = addAward")
    @Test
    void addAwardHappyPath() {
        Director director = getDirector();
        DirectorAwardDTO awardDTO = getAwardDTO();

        when(directorRepository.findById(anyLong())).thenReturn(Optional.of(director));

        DirectorAwardDTO returnDTO = directorService.addAward(1L, awardDTO);

        assertEquals(returnDTO.getCountry(), COUNTRY);
        assertEquals(returnDTO.getDate(), DATE);
        assertEquals(returnDTO.getAwardCategory(), CATEGORY);

        verify(directorRepository, times(1)).save(any(Director.class));
        verify(awardRepository, times(1)).save(any(DirectorAward.class));
    }


    @DisplayName("UnHappy Path, method = addAward")
    @Test
    void addAwardUnHappyPath() {
        DirectorAwardDTO awardDTO = new DirectorAwardDTO();
        Throwable ex = catchThrowable(() -> directorService.addAward(444L, awardDTO));

        assertThat(ex).isInstanceOf(ResourceNotFoundException.class);
    }


    @DisplayName("Happy Path, method = updateDirector")
    @Test
    void updateDirectorHappyPath() {
        DirectorDTO directorDTO = getDirectorDTO();
        directorDTO.setFirstName("Checking");
        Director director = getDirector();

        when(directorRepository.findById(anyLong())).thenReturn(Optional.of(director));

        DirectorDTO returnDTO = directorService.updateDirector(1L, directorDTO);

        assertEquals(returnDTO.getFirstName(), "Checking");
    }


    @DisplayName("UnHappy Path, method = updateDirector")
    @Test
    void updateDirectorUnHappyPath() {
        DirectorDTO directorDTO = new DirectorDTO();
        Throwable ex = catchThrowable(() -> directorService.updateDirector(1L, directorDTO));

        assertThat(ex).isInstanceOf(ResourceNotFoundException.class);
    }


    @DisplayName("Happy Path, method = updateAward")
    @Test
    void updateAwardHappyPath() {
        Director director = getDirector();
        DirectorAward award = getAward();
        director.getAwards().add(award);
        award.setDirector(director);
        DirectorAwardDTO awardDTO = getAwardDTO();
        awardDTO.setCountry("Checking");

        when(directorRepository.findById(anyLong())).thenReturn(Optional.of(director));

        DirectorAwardDTO returnDTO = directorService.updateAward(1L, 1L, awardDTO);

        assertEquals(returnDTO.getCountry(), "Checking");

        verify(directorRepository, times(1)).save(any(Director.class));
        verify(awardRepository, times(1)).save(any(DirectorAward.class));
    }


    @DisplayName("UnHappy Path, method = updateAward")
    @Test
    void updateAwardUnHappy() {
        DirectorAwardDTO awardDTO = new DirectorAwardDTO();
        Throwable ex = catchThrowable(() -> directorService.updateAward(1L, 1L, awardDTO));

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


    @DisplayName("Happy Path, method = deleteAwardByID")
    @Test
    void deleteAwardByIDHappyPath() {
        Director director = getDirector();
        director.getAwards().add(getAward());

        when(directorRepository.findById(anyLong())).thenReturn(Optional.of(director));

        directorService.deleteAwardByID(1L, 1L);

        verify(awardRepository, times(1)).deleteById(1L);
    }


    @DisplayName("UnHappy Path, method = deleteAwardByID")
    @Test
    void deleteAwardByIDUnHappy() {
        Throwable ex = catchThrowable(() -> directorService.deleteAwardByID(1L, 1L));

        assertThat(ex).isInstanceOf(ResourceNotFoundException.class);
    }
}
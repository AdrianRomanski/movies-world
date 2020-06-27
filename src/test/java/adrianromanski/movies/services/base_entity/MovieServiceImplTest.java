package adrianromanski.movies.services.base_entity;


import adrianromanski.movies.domain.award.MovieAward;
import adrianromanski.movies.domain.base_entity.Movie;
import adrianromanski.movies.domain.person.Actor;
import adrianromanski.movies.exceptions.ResourceNotFoundException;
import adrianromanski.movies.jms.JmsTextMessageService;
import adrianromanski.movies.mapper.award.MovieAwardMapper;
import adrianromanski.movies.mapper.award.MovieAwardMapperImpl;
import adrianromanski.movies.mapper.base_entity.MovieMapper;
import adrianromanski.movies.mapper.base_entity.MovieMapperImpl;
import adrianromanski.movies.model.award.MovieAwardDTO;
import adrianromanski.movies.model.base_entity.MovieDTO;
import adrianromanski.movies.repositories.ActorRepository;
import adrianromanski.movies.repositories.AwardRepository;
import adrianromanski.movies.repositories.MovieRepository;
import adrianromanski.movies.services.movie.MovieService;
import adrianromanski.movies.services.movie.MovieServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class MovieServiceImplTest {

    public static final String NAME = "Star Wars";
    public static final String GOOD_MOVIE = "Good movie";
    public static final String CATEGORY = "Hottest movie";
    public static final String COUNTRY = "Poland";
    public static final LocalDate DATE = LocalDate.now();

    @Mock
    MovieRepository movieRepository;

    @Mock
    ActorRepository actorRepository;

    @Mock
    AwardRepository awardRepository;

    @Mock
    JmsTextMessageService jms;

    MovieService movieService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        MovieAwardMapper awardMapper = new MovieAwardMapperImpl();
        MovieMapper movieMapper = new MovieMapperImpl(); // I have to do something with this ugly init - That should be singleton!!!!!
        movieService = new MovieServiceImpl(movieRepository, actorRepository,awardRepository, jms,
                                            movieMapper, awardMapper);
    }

    private List<Movie> getMovies() { return Arrays.asList(new Movie(), new Movie(), new Movie()); }

    private Movie getStarWars() { return Movie.builder().name(NAME).awards(Arrays.asList(getAward(), getAward())).build(); }

    private MovieAward getAward() { return MovieAward.builder().awardCategory(CATEGORY).country(COUNTRY).date(DATE).build(); }

    private MovieAwardDTO getAwardDTO() { return MovieAwardDTO.builder().awardCategory(CATEGORY).country(COUNTRY).date(DATE).build(); }


    @DisplayName("Happy Path, method = getAllMovies")
    @Test
    void getAllMovies() {
        List<Movie> movies = getMovies();

        when(movieRepository.findAll()).thenReturn(movies);

        List<MovieDTO> returnDTO = movieService.getAllMovies();

        assertEquals(returnDTO.size(), 3);
    }


    @DisplayName("Happy Path, method = getMovieByID")
    @Test
    void getMovieByIDHappyPath() {
        Movie movie = getStarWars();

        when(movieRepository.findById(anyLong())).thenReturn(Optional.of(movie));

        MovieDTO returnDTO = movieService.getMovieByID(1L);

        assertEquals(returnDTO.getName(), NAME);
    }


    @DisplayName("UnHappyPath, method = findAllMoviesWithActor")
    @Test
    void getMovieByIDUnHappyPath() {
        Throwable ex = catchThrowable(() -> movieService.getMovieByID(4442L));

        assertThat(ex).isInstanceOf(ResourceNotFoundException.class);
    }


    @DisplayName("Happy Path, method = getMovieByName")
    @Test
    void getMovieByNameHappyPath() {
        Movie movie = getStarWars();

        when(movieRepository.findByName(anyString())).thenReturn(Optional.of(movie));

        MovieDTO returnDTO = movieService.getMovieByName(NAME);

        assertEquals(returnDTO.getName(), NAME);
    }


    @DisplayName("UnHappyPath, method = getMovieByName")
    @Test
    void getMovieByNameUnHappyPath() {
        Throwable ex = catchThrowable(() -> movieService.getMovieByName("Breaking Bad"));

        assertThat(ex).isInstanceOf(ResourceNotFoundException.class);
    }


    @DisplayName("Happy Path, method = findAllMoviesWithActor")
    @Test
    void findAllMoviesWithActorHappyPath() {
        List<Movie> movies = getMovies();
        Actor actor = Actor.builder().firstName("Arnold").movies(movies).build();

        when(actorRepository.findByFirstNameAndAndLastName(anyString(), anyString())).thenReturn(Optional.of(actor));

        List<MovieDTO> returnDTO = movieService.findAllMoviesWithActor("Arnold", "Arnold");

        assertEquals(returnDTO.size(), 3);
    }


    @DisplayName("UnHappyPath, method = findAllMoviesWithActor")
    @Test
    void findAllMoviesWithActorUnHappyPath() {
        Throwable ex = catchThrowable(() -> movieService.findAllMoviesWithActor("Spider", "Chum"));

        assertThat(ex).isInstanceOf(ResourceNotFoundException.class);
    }


    @DisplayName("Happy Path, method = createMovie")
    @Test
    void createMovie() {
        MovieDTO movieDTO = new MovieDTO();
        movieDTO.setDescription(GOOD_MOVIE);

        MovieDTO returnDTO = movieService.createMovie(movieDTO);

        verify(movieRepository, times(1)).save(any(Movie.class));

        assertEquals(returnDTO.getDescription(), GOOD_MOVIE);
    }


    @DisplayName("Happy Path, method = addAward")
    @Test
    void addAwardHappyPath() {
       Movie movie = new Movie();
       MovieAwardDTO awardDTO = getAwardDTO();

       when(movieRepository.findById(anyLong())).thenReturn(Optional.of(movie));

       MovieAwardDTO returnDTO = movieService.addAward(1L, awardDTO);

       assertEquals(returnDTO.getAwardCategory(), CATEGORY);
       assertEquals(returnDTO.getCountry(), COUNTRY);
       assertEquals(returnDTO.getDate(), DATE);
    }


    @DisplayName("UnHappy Path, method = addAward")
    @Test
    void addAwardUnHappyPath() {
        MovieAwardDTO awardDTO = new MovieAwardDTO();
        Throwable ex = catchThrowable(() -> movieService.addAward(1L, awardDTO));

        assertThat(ex).isInstanceOf(ResourceNotFoundException.class);
    }


    @DisplayName("Happy Path, method = updateMovie")
    @Test
    void updateMovieHappyPath() {
        MovieDTO movieDTO = new MovieDTO();
        movieDTO.setDescription(GOOD_MOVIE);
        Movie movie = getStarWars();

        when(movieRepository.findById(anyLong())).thenReturn(Optional.of(movie));

        MovieDTO returnDTO = movieService.updateMovie(1L, movieDTO);

        verify(movieRepository, times(1)).save(any(Movie.class));

        assertEquals(returnDTO.getDescription(), GOOD_MOVIE);
    }


    @DisplayName("UnHappy Path, method = updateMovie")
    @Test
    void updateMovieUnHappyPath() {
        MovieDTO movieDTO = new MovieDTO();

        Throwable ex = catchThrowable(() -> movieService.updateMovie(1L, movieDTO));

        assertThat(ex).isInstanceOf(ResourceNotFoundException.class);
    }

    
    @DisplayName("Happy Path, method = updateAward")
    @Test
    void updateAwardHappyPath() {
        Movie movie = new Movie();
        MovieAward award = new MovieAward();
        award.setId(1L);
        movie.getAwards().add(award);
        award.setMovie(movie);
        MovieAwardDTO awardDTO = new MovieAwardDTO();
        awardDTO.setCountry(COUNTRY);

        when(movieRepository.findById(anyLong())).thenReturn(Optional.of(movie));

        MovieAwardDTO returnDTO = movieService.updateAward(1L, 1L, awardDTO);

        assertEquals(returnDTO.getCountry(), COUNTRY);
    }


    @DisplayName("UnHappy Path, method = updateAward")
    @Test
    void updateAwardUnHappyPath() {
        MovieAwardDTO awardDTO = new MovieAwardDTO();
        Throwable ex = catchThrowable(() -> movieService.updateAward(1L, 1L, awardDTO));

        assertThat(ex).isInstanceOf(ResourceNotFoundException.class);
    }


    @DisplayName("Happy Path, method = deleteMovieByID")
    @Test
    void deleteMovieByIDHappyPath() {
        Movie movie = new Movie();

        when(movieRepository.findById(anyLong())).thenReturn(Optional.of(movie));

        movieService.deleteMovieByID(1L);

        verify(movieRepository, times(1)).deleteById(1L);
    }


    @DisplayName("UnHappy Path, method = deleteMovieByID")
    @Test
    void deleteMovieByIDUnHappyPath() {
        Throwable ex = catchThrowable(() -> movieService.deleteMovieByID(1L));

        assertThat(ex).isInstanceOf(ResourceNotFoundException.class);
    }


    @DisplayName("Happy Path, method = deleteAwardByID")
    @Test
    void deleteAwardByIDHappyPath() {
        Movie movie = new Movie();
        MovieAward award = new MovieAward();
        award.setId(1L);
        movie.getAwards().add(award);
        award.setMovie(movie);

        when(movieRepository.findById(anyLong())).thenReturn(Optional.of(movie));

        movieService.deleteAwardByID(1L, 1L);

        verify(awardRepository, times(1)).deleteById(1L);
    }


    @DisplayName("UnHappy Path, method = deleteAwardByID")
    @Test
    void deleteAwardByIDUnHappyPath() {
        Throwable ex = catchThrowable(() -> movieService.deleteAwardByID(1L, 1L));

        assertThat(ex).isInstanceOf(ResourceNotFoundException.class);
    }

}
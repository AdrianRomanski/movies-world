package adrianromanski.movies.services;


import adrianromanski.movies.domain.Actor;
import adrianromanski.movies.domain.Movie;
import adrianromanski.movies.exceptions.ResourceNotFoundException;
import adrianromanski.movies.jms.JmsTextMessageService;
import adrianromanski.movies.mapper.ActorMapper;
import adrianromanski.movies.mapper.ActorMapperImpl;
import adrianromanski.movies.mapper.MovieMapper;
import adrianromanski.movies.mapper.MovieMapperImpl;
import adrianromanski.movies.model.MovieDTO;
import adrianromanski.movies.repositories.ActorRepository;
import adrianromanski.movies.repositories.MovieRepository;
import adrianromanski.movies.services.movie.MovieService;
import adrianromanski.movies.services.movie.MovieServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

class MovieServiceImplTest {

    public static final String NAME = "Star Wars";
    @Mock
    MovieRepository movieRepository;

    @Mock
    ActorRepository actorRepository;

    @Mock
    JmsTextMessageService jms;

    MovieService movieService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        MovieMapper movieMapper = new MovieMapperImpl(); // I have to do something with this ugly init - That should be singleton!!!!!
        movieService = new MovieServiceImpl(movieRepository, actorRepository, jms, movieMapper);
    }

    private List<Movie> getMovies() { return Arrays.asList(new Movie(), new Movie(), new Movie()); }

    private Movie getStarWars() { return Movie.builder().name(NAME).build(); }


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
}
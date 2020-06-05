package adrianromanski.movies.services;


import adrianromanski.movies.domain.Movie;
import adrianromanski.movies.jms.JmsTextMessageService;
import adrianromanski.movies.jms.JmsTextMessageServiceImpl;
import adrianromanski.movies.mapper.MovieMapper;
import adrianromanski.movies.mapper.MovieMapperImpl;
import adrianromanski.movies.model.MovieDTO;
import adrianromanski.movies.repositories.MovieRepository;
import adrianromanski.movies.services.movie.MovieService;
import adrianromanski.movies.services.movie.MovieServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class MovieServiceImplTest {

    @Mock
    MovieRepository movieRepository;

    @Mock
    JmsTextMessageService jms;

    MovieService movieService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        MovieMapper movieMapper = new MovieMapperImpl(); // I have to do something with this ugly init - That should be singleton!!!!!
        movieService = new MovieServiceImpl(movieRepository, jms, movieMapper);
    }

    @Test
    void findAll() {
        List<Movie> movies = Arrays.asList(new Movie(), new Movie(), new Movie());

        when(movieRepository.findAll()).thenReturn(movies);

        List<MovieDTO> returnDTO = movieService.findAll();

        assertEquals(returnDTO.size(), 3);
    }
}
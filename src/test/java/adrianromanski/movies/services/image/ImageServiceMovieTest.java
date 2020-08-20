package adrianromanski.movies.services.image;

import adrianromanski.movies.domain.base_entity.Movie;
import adrianromanski.movies.mapper.base_entity.MovieMapper;
import adrianromanski.movies.mapper.base_entity.MovieMapperImpl;
import adrianromanski.movies.model.base_entity.MovieDTO;
import adrianromanski.movies.repositories.base_entity.MovieRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class ImageServiceMovieTest {

    @Mock
    MovieRepository movieRepository;


    ImageServiceMovie imageService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);

        MovieMapper movieMapper = new MovieMapperImpl();

        imageService = new ImageServiceMovie(movieRepository, movieMapper);
    }


    @Test
    @DisplayName("Happy Path, method = saveImageFile")
    void saveImageFile() throws IOException {
        //given
        Long id = 1L;
        MultipartFile multipartFile = new MockMultipartFile("imagefile", "testing.txt", "text/plain",
                "Spring Framework Guru".getBytes());

        Movie movie = new Movie();
        movie.setId(id);
        Optional<Movie> movieOptional = Optional.of(movie);

        MovieDTO movieDTO = new MovieDTO();

        when(movieRepository.findByName(anyString())).thenReturn(movieOptional);

        ArgumentCaptor<Movie> argumentCaptor = ArgumentCaptor.forClass(Movie.class);

        //when
        imageService.saveImageFile(movieDTO, multipartFile);

        //then
        verify(movieRepository, times(1)).save(argumentCaptor.capture());

        Movie savedMovie = argumentCaptor.getValue();
        assertEquals(multipartFile.getBytes().length, savedMovie.getImage().length);
    }
}
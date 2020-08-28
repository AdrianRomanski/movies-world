package adrianromanski.movies.controllers.user;

import adrianromanski.movies.domain.base_entity.Movie;
import adrianromanski.movies.model.base_entity.MovieDTO;
import adrianromanski.movies.services.movie.MovieServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class MoviesControllerTest {

    @Mock
    MovieServiceImpl movieService;

    @InjectMocks
    MoviesController controller;

    MockMvc mockMvc;

    Movie movie;
    MovieDTO movieDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);

        movie = Movie.builder().name("Shrek").minutes(90L)
                .description("A mean lord exiles fairytale creatures to the swamp of a grumpy ogre, who must go on a quest and rescue a princess for the lord in order to get his land back.").build();

        movieDTO = MovieDTO.builder().name("Shrek").minutes(90L)
                .description("A mean lord exiles fairytale creatures to the swamp of a grumpy ogre, who must go on a quest and rescue a princess for the lord in order to get his land back.").build();

        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }


    @Test
    @DisplayName("GET, Happy Path, method = showMovie")
    void showMovie() throws Exception {
        when(movieService.getMovieByID(anyLong())).thenReturn(movieDTO);

        mockMvc.perform(get("/movie/1"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("movie"))
                .andExpect(view().name("user/movies/showMovie"));
    }


    @Test
    @DisplayName("GET, Happy Path, method = showMovies")
    void showMovies() throws Exception {
        // Given
        List<Movie> movieList = Arrays.asList(new Movie(), new Movie(), new Movie());
        PageRequest pageable = PageRequest.of(0, 8);
        Page<Movie> moviePage = new PageImpl<>(movieList, pageable, movieList.size());

        List<MovieDTO> movieListDTO = Arrays.asList(new MovieDTO(), new MovieDTO(), new MovieDTO());
        PageRequest pageableDTO = PageRequest.of(0, 8);
        Page<MovieDTO> moviePageDTO = new PageImpl<>(movieListDTO, pageableDTO, movieListDTO.size());

        when(movieService.getAllMoviesPaged(pageable)).thenReturn(moviePage);
        when(movieService.getPageMovieDTO(moviePage, pageable)).thenReturn(moviePageDTO);


        mockMvc.perform(get("/movies/page/1"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("activeMoviesList"))
                .andExpect(model().attributeExists("moviesDTOList"))
                .andExpect(model().attributeExists("pageNumbers"))
                .andExpect(view().name("user/movies/showMovies"));
    }


    @Test
    @DisplayName("GET, Happy Path, method = showMoviesSortedByName")
    void showMoviesSortedByName() throws Exception {
        // Given
        List<Movie> movieList = Arrays.asList(movie, movie, movie);
        PageRequest pageable = PageRequest.of(0, 8, Sort.by("name"));
        Page<Movie> moviePage = new PageImpl<>(movieList, pageable, movieList.size());

        List<MovieDTO> movieListDTO = Arrays.asList(movieDTO, movieDTO, movieDTO);
        PageRequest pageableDTO = PageRequest.of(0, 8, Sort.by("name"));
        Page<MovieDTO> moviePageDTO = new PageImpl<>(movieListDTO, pageableDTO, movieListDTO.size());

        when(movieService.getAllMoviesPaged(pageable)).thenReturn(moviePage);
        when(movieService.getPageMovieDTO(moviePage, pageable)).thenReturn(moviePageDTO);


        mockMvc.perform(get("/movies/sorted/name/page/1"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("activeMoviesList"))
                .andExpect(model().attributeExists("moviesDTOList"))
                .andExpect(model().attributeExists("pageNumbers"))
                .andExpect(view().name("user/movies/moviesSortedByName"));
    }
}
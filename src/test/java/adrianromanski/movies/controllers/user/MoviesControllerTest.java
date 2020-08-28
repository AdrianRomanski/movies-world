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

    public static final String DESC = "mean lord exiles fairytale creatures to the swamp of a grumpy ogre, who must go on a quest and rescue a princess for the lord in order to get his land back.";
    public static final String SHREK = "Shrek";
    @Mock
    MovieServiceImpl movieService;

    @InjectMocks
    MoviesController controller;

    MockMvc mockMvc;

    Movie movie1;
    Movie movie2;
    Movie movie3;
    MovieDTO movieDTO1;
    MovieDTO movieDTO2;
    MovieDTO movieDTO3;

    List<Movie> movieList;
    List<MovieDTO> movieListDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);

        movie1 = Movie.builder().name(SHREK + " 1").time(90L)
                .description("A " + DESC).build();
        movie2 = Movie.builder().name(SHREK + " 2").time(100L)
                .description("B " + DESC).build();
        movie3 = Movie.builder().name(SHREK + " 3").time(110L)
                .description("C " + DESC).build();

        movieList = Arrays.asList(movie1, movie2, movie3);

        movieDTO1 = MovieDTO.builder().name(SHREK + " 1").time(90L)
                .description("A " + DESC).build();
        movieDTO2 = MovieDTO.builder().name(SHREK + " 2").time(100L)
                .description("B " + DESC).build();
        movieDTO3 = MovieDTO.builder().name(SHREK + " 3").time(110L)
                .description("C " + DESC).build();

        movieListDTO = Arrays.asList(movieDTO1, movieDTO2, movieDTO3);

        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }


    @Test
    @DisplayName("GET, Happy Path, method = showMovie")
    void showMovie() throws Exception {
        when(movieService.getMovieByID(anyLong())).thenReturn(movieDTO1);

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
    @DisplayName("GET, Happy Path, method = showMoviesSortedDescending")
    void showMoviesSortedDesc() throws Exception {
        // Given
        PageRequest pageable = PageRequest.of(0, 8, Sort.by("time").descending());
        Page<Movie> moviePage = new PageImpl<>(movieList, pageable, movieList.size());

        PageRequest pageableDTO = PageRequest.of(0, 8, Sort.by("time").descending());
        Page<MovieDTO> moviePageDTO = new PageImpl<>(movieListDTO, pageableDTO, movieListDTO.size());

        when(movieService.getAllMoviesPaged(pageable)).thenReturn(moviePage);
        when(movieService.getPageMovieDTO(moviePage, pageable)).thenReturn(moviePageDTO);


        mockMvc.perform(get("/movies/sorted/desc/time/page/1"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("activeMoviesList"))
                .andExpect(model().attributeExists("moviesDTOList"))
                .andExpect(model().attributeExists("pageNumbers"))
                .andExpect(view().name("user/movies/moviesSorted"));
    }

    @Test
    @DisplayName("GET, Happy Path, method = showMoviesSortedAscending")
    void showMoviesSortedAsc() throws Exception {
        // Given
        PageRequest pageable = PageRequest.of(0, 8, Sort.by("time").ascending());
        Page<Movie> moviePage = new PageImpl<>(movieList, pageable, movieList.size());

        PageRequest pageableDTO = PageRequest.of(0, 8, Sort.by("time").ascending());
        Page<MovieDTO> moviePageDTO = new PageImpl<>(movieListDTO, pageableDTO, movieListDTO.size());

        when(movieService.getAllMoviesPaged(pageable)).thenReturn(moviePage);
        when(movieService.getPageMovieDTO(moviePage, pageable)).thenReturn(moviePageDTO);


        mockMvc.perform(get("/movies/sorted/asc/time/page/1"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("activeMoviesList"))
                .andExpect(model().attributeExists("moviesDTOList"))
                .andExpect(model().attributeExists("pageNumbers"))
                .andExpect(view().name("user/movies/moviesSorted"));
    }
}
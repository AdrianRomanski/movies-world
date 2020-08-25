package adrianromanski.movies.controllers.admin;

import adrianromanski.movies.domain.base_entity.Movie;
import adrianromanski.movies.mapper.base_entity.MovieMapper;
import adrianromanski.movies.mapper.base_entity.MovieMapperImpl;
import adrianromanski.movies.model.base_entity.MovieDTO;
import adrianromanski.movies.services.category.CategoryService;
import adrianromanski.movies.services.movie.MovieService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


class AdminMovieControllerTest {

    public static final String DESCRIPTION = "A horror film is a film that seeks to elicit fear for entertainment purposes Initially " +
            "inspired by literature from authors such as Edgar Allan Poe, Bram Stoker, and Mary Shelley";
    public static final String NAME = "Horror";

    @Mock
    MovieService movieService;

    @Mock
    CategoryService categoryService;

    AdminMovieController controller;

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);

        MovieMapper mapper = new MovieMapperImpl();

        controller = new AdminMovieController(movieService, categoryService, mapper);

        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }


    @Test
    @DisplayName("Happy Path, method = showMoviesPaged")
    void showMoviesPaged() throws Exception {
        // Given
        List<Movie> movieList = Arrays.asList(new Movie(), new Movie(), new Movie());
        PageRequest pageable = PageRequest.of(0, 5);
        Page<Movie> moviePage = new PageImpl<>(movieList, pageable, movieList.size());

        List<MovieDTO> movieListDTO = Arrays.asList(new MovieDTO(), new MovieDTO(), new MovieDTO());
        PageRequest pageableDTO = PageRequest.of(0, 5);
        Page<MovieDTO> moviePageDTO = new PageImpl<>(movieListDTO, pageableDTO, movieListDTO.size());

        //When
        when(movieService.getAllMoviesPaged(pageable)).thenReturn(moviePage);
        when(movieService.getPageMovieDTO(moviePage, pageable)).thenReturn(moviePageDTO);

        //Then
        mockMvc.perform(get("/admin/showMovies/page/1"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("moviesDTOList"))
                .andExpect(view().name("admin/movie/showMoviesForm"));
    }


    @Test
    @DisplayName("Happy Path, method = createMovie")
    void createMovie() throws Exception {
        mockMvc.perform(get("/admin/createMovie"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("movieDTO"))
                .andExpect(view().name("admin/movie/createMovieForm"));
    }


    @Test
    @DisplayName("Happy Path, method = createMovie")
    void checkMovieCreationHappyPath() throws Exception {
        //given
        MovieDTO movieDTO = new MovieDTO();

        //when
        when(movieService.createMovie(any())).thenReturn(movieDTO);

        mockMvc.perform(post("/admin/createMovie/check")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("name", NAME)
                .param("description", DESCRIPTION)
        )
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("movieDTO"))
                .andExpect(view().name("admin/movie/selectCategoryForMovieForm"));
    }


    @Test
    @DisplayName("Unhappy Path, method = createMovie")
    void checkMovieCreationUnHappyPath() throws Exception {
        //given
        MovieDTO movieDTO = new MovieDTO();

        //when
        when(movieService.createMovie(any())).thenReturn(movieDTO);

        mockMvc.perform(post("/admin/createMovie/check")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("name", "n")
                .param("description", "n")
        )
                .andExpect(model().attributeExists("movieDTO"))
                .andExpect(view().name("admin/movie/createMovieForm"));
    }



    @Test
    @DisplayName("Happy Path, method = addCategoryToMovie")
    void addCategoryToMovie() throws Exception {
        mockMvc.perform(get("/admin/createMovie-1/addCategory-1"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("movies"))
                .andExpect(view().name("admin/movie/movieImageUplForm"));
    }


    @Test
    @DisplayName("Happy Path, method = updateMovie")
    void updateMovie() throws Exception {
        mockMvc.perform(get("/admin/updateMovie/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/movie/updateMovieForm"));
    }


    @Test
    @DisplayName("Happy Path, method = checkMovieUpdate")
    void checkMovieUpdateHappyPath() throws Exception {
        //given
        MovieDTO movieDTO = new MovieDTO();

        //when
        when(movieService.createMovie(any())).thenReturn(movieDTO);

        mockMvc.perform(post("/admin/updateMovie/check")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("name", NAME)
                .param("description", DESCRIPTION)
        )
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("movieDTO"))
                .andExpect(view().name("admin/adminHome"));
    }


    @Test
    @DisplayName("Unhappy Path, method = checkMovieUpdate")
    void checkMovieUpdateUnHappyPath() throws Exception {
        //given
        MovieDTO movieDTO = new MovieDTO();

        //when
        when(movieService.createMovie(any())).thenReturn(movieDTO);

        mockMvc.perform(post("/admin/updateMovie/check")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("name", "n")
                .param("description", "n")
        )
                .andExpect(model().attributeExists("movieDTO"))
                .andExpect(view().name("admin/movie/updateMovieForm"));
    }


    @Test
    @DisplayName("Happy Path, method = deleteMovie")
    void deleteMovie() throws Exception {
        mockMvc.perform(get("/admin/deleteMovie/1"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("movies"))
                .andExpect(view().name("admin/adminHome"));
    }
}
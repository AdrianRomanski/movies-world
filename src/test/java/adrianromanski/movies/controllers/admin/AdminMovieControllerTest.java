package adrianromanski.movies.controllers.admin;

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
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

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
    @DisplayName("Happy Path, method = showMovies")
    void showMovies() throws Exception {
        mockMvc.perform(get("/admin/showMovies"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("movies"))
                .andExpect(view().name("admin/movie/showMoviesForm"));
    }


    @Test
    @DisplayName("Happy Path, method = createMovie")
    void createMovie() throws Exception {
        mockMvc.perform(get("/admin/createMovie"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("movie"))
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
                .andExpect(model().attributeExists("movie"))
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
                .andExpect(model().attributeExists("movie"))
                .andExpect(view().name("admin/movie/createMovieForm"));
    }



    @Test
    @DisplayName("Happy Path, method = addCategoryToMovie")
    void addCategoryToMovie() throws Exception {
        mockMvc.perform(get("/admin/createMovie-1/addCategory-1"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("movies"))
                .andExpect(view().name("admin/movie/showMoviesForm"));
    }
}
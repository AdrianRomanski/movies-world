package adrianromanski.movies.controllers.admin;

import adrianromanski.movies.mapper.base_entity.MovieMapper;
import adrianromanski.movies.mapper.base_entity.MovieMapperImpl;
import adrianromanski.movies.repositories.base_entity.MovieRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


class AdminMovieControllerTest {

    @Mock
    MovieRepository movieRepository;

    AdminMovieController controller;

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);

        MovieMapper mapper = new MovieMapperImpl();

        controller = new AdminMovieController(movieRepository, mapper);

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
}
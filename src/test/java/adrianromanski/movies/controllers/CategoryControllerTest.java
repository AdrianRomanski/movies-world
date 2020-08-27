package adrianromanski.movies.controllers;

import adrianromanski.movies.controllers.user.CategoryController;
import adrianromanski.movies.domain.base_entity.Movie;
import adrianromanski.movies.model.base_entity.MovieDTO;
import adrianromanski.movies.services.category.CategoryService;
import adrianromanski.movies.services.movie.MovieService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class CategoryControllerTest {

    @Mock
    CategoryService categoryService;

    @Mock
    MovieService movieService;

    @InjectMocks
    CategoryController categoryController;

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);

        mockMvc = MockMvcBuilders.standaloneSetup(categoryController).build();
    }

    @Test
    @DisplayName("GET, HappyPath, method = getAllMoviesForCategory")
    void getAllMoviesForCategory() throws Exception {
        List<MovieDTO> movies = Arrays.asList( new MovieDTO(),  new MovieDTO(), new MovieDTO());

        when(categoryService.getAllMoviesForCategory(anyString())).thenReturn(movies);

        mockMvc.perform(get("/category/fantasy"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("movies"));
    }

    @Test
    @DisplayName("GET, HappyPath, method = getAllCategories")
    void getAllCategories() throws Exception {
        mockMvc.perform(get("/categories"))
                .andExpect(status().isOk())
                .andExpect(view().name("user/categories"));
    }


    @Test
    @DisplayName("Happy Path, method = getAllMoviesForCategoryPaged")
    void getAllMoviesForCategoryPaged() throws Exception {
        // Given
        List<Movie> movieList = Arrays.asList(new Movie(), new Movie(), new Movie());
        PageRequest pageable = PageRequest.of(0, 8);
        Page<Movie> moviePage = new PageImpl<>(movieList, pageable, movieList.size());

        List<MovieDTO> movieListDTO = Arrays.asList(new MovieDTO(), new MovieDTO(), new MovieDTO());
        PageRequest pageableDTO = PageRequest.of(0, 8);
        Page<MovieDTO> moviePageDTO = new PageImpl<>(movieListDTO, pageableDTO, movieListDTO.size());

        //When
        when(movieService.getAllMoviesForCategoryPaged("Sci-fi", pageable)).thenReturn(moviePage);
        when(movieService.getPageMovieDTO(moviePage, pageable)).thenReturn(moviePageDTO);

        //Then
        mockMvc.perform(get("/category/Sci-fi/page/1"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("moviesDTOList"))
                .andExpect(view().name("user/categoryMovies"));
    }
}
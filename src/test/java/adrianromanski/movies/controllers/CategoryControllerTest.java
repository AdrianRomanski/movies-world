package adrianromanski.movies.controllers;

import adrianromanski.movies.controllers.user.CategoryController;
import adrianromanski.movies.model.base_entity.MovieDTO;
import adrianromanski.movies.services.category.CategoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class CategoryControllerTest {

    @Mock
    CategoryService categoryService;

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
}
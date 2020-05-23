package adrianromanski.movies.services;

import adrianromanski.movies.domain.Category;
import adrianromanski.movies.domain.Movie;
import adrianromanski.movies.exceptions.ResourceNotFoundException;
import adrianromanski.movies.mapper.CategoryMapper;
import adrianromanski.movies.mapper.CategoryMapperImpl;
import adrianromanski.movies.mapper.MovieMapper;
import adrianromanski.movies.mapper.MovieMapperImpl;
import adrianromanski.movies.model.CategoryDTO;
import adrianromanski.movies.model.MovieDTO;
import adrianromanski.movies.repositories.CategoryRepository;
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
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

class CategoryServiceImplTest {

    @Mock
    CategoryRepository categoryRepository;

    CategoryService categoryService;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);

        CategoryMapper categoryMapper = new CategoryMapperImpl();
        MovieMapper movieMapper = new MovieMapperImpl();
        categoryService = new CategoryServiceImpl(categoryRepository, categoryMapper, movieMapper);
    }

    @DisplayName("Happy Path, method = getAllCategories")
    @Test
    void getAllCategories() {
        List<Category> categories = Arrays.asList(new Category(), new Category(), new Category());

        when(categoryRepository.findAll()).thenReturn(categories);

        List<CategoryDTO> returnDTO = categoryService.getAllCategories();

        assertEquals(returnDTO.size(), 3);
    }

    @DisplayName("Happy Path, method = getAllMoviesForCategory")
    @Test
    void getAllMoviesForCategoryHappyPath() {
        List<Movie> movies = Arrays.asList(new Movie(), new Movie(), new Movie());
        Category category = Category.builder().movies(movies).build();

        when(categoryRepository.findByName(anyString())).thenReturn(Optional.of(category));

        List<MovieDTO> returnDTO = categoryService.getAllMoviesForCategory(anyString());

        assertEquals(returnDTO.size(), 3);
    }

    @DisplayName("UnhappyPath, method = getAllMoviesForCategory")
    @Test
    void getAllMovieForCategoryUnHappyPath() {
        Throwable ex = catchThrowable(() -> categoryService.getAllMoviesForCategory("24421"));

        assertThat(ex).isInstanceOf(ResourceNotFoundException.class);
    }
}
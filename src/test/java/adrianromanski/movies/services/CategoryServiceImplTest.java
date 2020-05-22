package adrianromanski.movies.services;

import adrianromanski.movies.domain.Category;
import adrianromanski.movies.mapper.CategoryMapper;
import adrianromanski.movies.mapper.CategoryMapperImpl;
import adrianromanski.movies.mapper.MovieMapper;
import adrianromanski.movies.mapper.MovieMapperImpl;
import adrianromanski.movies.model.CategoryDTO;
import adrianromanski.movies.repositories.CategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
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
}
package adrianromanski.movies.services.base_entity;

import adrianromanski.movies.domain.base_entity.Category;
import adrianromanski.movies.domain.base_entity.Movie;
import adrianromanski.movies.exceptions.ResourceNotFoundException;
import adrianromanski.movies.jms.JmsTextMessageService;
import adrianromanski.movies.mapper.base_entity.CategoryMapper;
import adrianromanski.movies.mapper.base_entity.CategoryMapperImpl;
import adrianromanski.movies.mapper.base_entity.MovieMapper;
import adrianromanski.movies.mapper.base_entity.MovieMapperImpl;
import adrianromanski.movies.model.base_entity.CategoryDTO;
import adrianromanski.movies.model.base_entity.MovieDTO;
import adrianromanski.movies.repositories.CategoryRepository;
import adrianromanski.movies.services.category.CategoryService;
import adrianromanski.movies.services.category.CategoryServiceImpl;
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
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class CategoryServiceImplTest {

    public static final String NAME = "Fantasy";
    @Mock
    CategoryRepository categoryRepository;

    @Mock
    JmsTextMessageService jms;

    CategoryService categoryService;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);

        CategoryMapper categoryMapper = new CategoryMapperImpl();
        MovieMapper movieMapper = new MovieMapperImpl();
        categoryService = new CategoryServiceImpl(categoryRepository, jms, categoryMapper, movieMapper);
    }

    private Category getCategory() {
        return Category.builder().name(NAME).build();
    }

    private CategoryDTO getCategoryDTO() {
        return CategoryDTO.builder().name(NAME).build();
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
        Category category = Category.builder().name("Fantasy").movies(movies).build();

        when(categoryRepository.findByName(anyString())).thenReturn(Optional.of(category));

        List<MovieDTO> returnDTO = categoryService.getAllMoviesForCategory("Fantasy");

        assertEquals(returnDTO.size(), 3);
    }


    @DisplayName("UnhappyPath, method = getAllMoviesForCategory")
    @Test
    void getAllMovieForCategoryUnHappyPath() {
        Throwable ex = catchThrowable(() -> categoryService.getAllMoviesForCategory("24421"));

        assertThat(ex).isInstanceOf(ResourceNotFoundException.class);
    }


    @DisplayName("Happy Path, method = getCategoryByName")
    @Test
    void getCategoryByName() {
        Category category = getCategory();

        when(categoryRepository.findByName(anyString())).thenReturn(Optional.of(category));

        CategoryDTO returnDTO = categoryService.getCategoryByName(NAME);

        assertEquals(returnDTO.getName(), NAME);
    }


    @DisplayName("UnhappyPath, method = getCategoryByName")
    @Test
    void getCategoryByNameUnhappyPath() {
        Throwable ex = catchThrowable(() -> categoryService.getCategoryByName("24421"));

        assertThat(ex).isInstanceOf(ResourceNotFoundException.class);
    }


    @DisplayName("Happy Path, method = createCategory")
    @Test
    void createCategory() {
        CategoryDTO categoryDTO = getCategoryDTO();

        CategoryDTO returnDTO = categoryService.createCategory(categoryDTO);

        assertEquals(returnDTO.getName(), NAME);

        verify(categoryRepository, times(1)).save(any(Category.class));
    }


    @DisplayName("Happy Path, method = updateCategory")
    @Test
    void updateCategoryHappyPath() {
        CategoryDTO categoryDTO = getCategoryDTO();
        categoryDTO.setName("Updated");
        Category category = getCategory();

        when(categoryRepository.findById(anyLong())).thenReturn(Optional.of(category));

        CategoryDTO returnDTO = categoryService.updateCategory(1L, categoryDTO);

        assertEquals(returnDTO.getName(), "Updated");

        verify(categoryRepository, times(1)).save(any(Category.class));
    }


    @DisplayName("UnhappyPath, method = updateCategory")
    @Test
    void updateCategoryUnHappyPath() {
        CategoryDTO categoryDTO = new CategoryDTO();
        Throwable ex = catchThrowable(() -> categoryService.updateCategory(22L, categoryDTO));

        assertThat(ex).isInstanceOf(ResourceNotFoundException.class);
    }


    @DisplayName("Happy Path, method = deleteCategoryByID")
    @Test
    void deleteCategoryByIDHappyPath() {
        Category category = getCategory();

        when(categoryRepository.findById(anyLong())).thenReturn(Optional.of(category));

        categoryService.deleteCategoryByID(1L);

        verify(categoryRepository, times(1)).deleteById(1L);
    }


    @DisplayName("UnhappyPath, method = deleteCategoryByID")
    @Test
    void deleteCategoryByIDUnhappyPath() {
        Throwable ex = catchThrowable(() -> categoryService.deleteCategoryByID(22L));

        assertThat(ex).isInstanceOf(ResourceNotFoundException.class);
     }
}
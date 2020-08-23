package adrianromanski.movies.mapper.base_entity;

import adrianromanski.movies.domain.base_entity.Category;
import adrianromanski.movies.domain.base_entity.Movie;
import adrianromanski.movies.model.base_entity.CategoryDTO;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CategoryMapperTest {

    public static final String NAME = "Fantasy";
    public static final String DESCRIPTION = "Fantasy movies rocks!";
    public static final long ID = 1L;



    CategoryMapper categoryMapper = new CategoryMapperImpl();

    @Test
    void categoryToCategoryDTO() {
        List<Movie> movieList = Arrays.asList(new Movie(), new Movie(), new Movie());
        Category category = Category.builder().name(NAME).description(DESCRIPTION).movies(movieList).build();

        CategoryDTO categoryDTO = categoryMapper.categoryToCategoryDTO(category);

        assertEquals(categoryDTO.getName(), NAME);
        assertEquals(categoryDTO.getDescription(), DESCRIPTION);
        assertEquals(categoryDTO.getMoviesDTO().size(), 3);
    }

    @Test
    void categoryDTOToCategory() {
        CategoryDTO categoryDTO = CategoryDTO.builder().name(NAME).description(DESCRIPTION).build();

        Category category = categoryMapper.categoryDTOToCategory(categoryDTO);

        assertEquals(category.getName(), NAME);
        assertEquals(category.getDescription(), DESCRIPTION);
    }
}
package adrianromanski.movies.mapper;

import adrianromanski.movies.domain.Category;
import adrianromanski.movies.model.CategoryDTO;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CategoryMapperTest {

    public static final String NAME = "Fantasy";
    public static final String DESCRIPTION = "Fantasy movies rocks!";
    public static final long ID = 1L;

    CategoryMapper categoryMapper = CategoryMapper.INSTANCE;

    @Test
    void categoryToCategoryDTO() {
        Category category = Category.builder().name(NAME).description(DESCRIPTION).build();
        category.setId(ID);

        CategoryDTO categoryDTO = categoryMapper.categoryToCategoryDTO(category);

        assertEquals(categoryDTO.getName(), NAME);
        assertEquals(categoryDTO.getDescription(), DESCRIPTION);
        assertEquals(categoryDTO.getId(), ID);
    }

    @Test
    void categoryDTOToCategory() {
        CategoryDTO categoryDTO = CategoryDTO.builder().name(NAME).description(DESCRIPTION).build();

        Category category = categoryMapper.categoryDTOToCategory(categoryDTO);

        assertEquals(category.getName(), NAME);
        assertEquals(category.getDescription(), DESCRIPTION);
    }
}
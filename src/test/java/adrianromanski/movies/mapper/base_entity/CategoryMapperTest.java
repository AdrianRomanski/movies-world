package adrianromanski.movies.mapper.base_entity;

import adrianromanski.movies.domain.base_entity.Category;
import adrianromanski.movies.model.base_entity.CategoryDTO;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CategoryMapperTest {

    public static final String NAME = "Fantasy";
    public static final String DESCRIPTION = "Fantasy movies rocks!";
    public static final long ID = 1L;

    CategoryMapper categoryMapper = new CategoryMapperImpl();

    @Test
    void categoryToCategoryDTO() {
        Category category = Category.builder().name(NAME).description(DESCRIPTION).build();

        CategoryDTO categoryDTO = categoryMapper.categoryToCategoryDTO(category);

        assertEquals(categoryDTO.getName(), NAME);
        assertEquals(categoryDTO.getDescription(), DESCRIPTION);
    }

    @Test
    void categoryDTOToCategory() {
        CategoryDTO categoryDTO = CategoryDTO.builder().name(NAME).description(DESCRIPTION).build();

        Category category = categoryMapper.categoryDTOToCategory(categoryDTO);

        assertEquals(category.getName(), NAME);
        assertEquals(category.getDescription(), DESCRIPTION);
    }
}
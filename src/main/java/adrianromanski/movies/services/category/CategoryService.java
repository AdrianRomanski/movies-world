package adrianromanski.movies.services.category;


import adrianromanski.movies.domain.base_entity.Category;
import adrianromanski.movies.model.base_entity.CategoryDTO;
import adrianromanski.movies.model.base_entity.MovieDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CategoryService {

    //GET
    List<CategoryDTO> getAllCategories();

    Page<Category> getAllCategoriesPaged(Pageable pageable);

    List<MovieDTO> getAllMoviesForCategory(String name);

    CategoryDTO getCategoryDTOByName(String name);

    Category getCategoryById(Long id);

    CategoryDTO getCategoryDTOById(Long id);

    //POST
    CategoryDTO createCategory(CategoryDTO categoryDTO);

    //PUT
    CategoryDTO updateCategory(Long id, CategoryDTO categoryDTO);

    //DELETE
    void deleteCategoryByID(Long id);

}

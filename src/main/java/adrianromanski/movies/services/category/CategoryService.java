package adrianromanski.movies.services.category;


import adrianromanski.movies.domain.base_entity.Category;
import adrianromanski.movies.model.base_entity.CategoryDTO;
import adrianromanski.movies.model.base_entity.MovieDTO;

import java.util.List;

public interface CategoryService {

    //GET
    List<CategoryDTO> getAllCategories();

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

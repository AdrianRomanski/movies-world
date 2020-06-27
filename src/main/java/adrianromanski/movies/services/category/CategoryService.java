package adrianromanski.movies.services.category;


import adrianromanski.movies.model.base_entity.CategoryDTO;
import adrianromanski.movies.model.base_entity.MovieDTO;

import java.util.List;

public interface CategoryService {

    //GET
    List<CategoryDTO> getAllCategories();

    List<MovieDTO> getAllMoviesForCategory(String name);

    CategoryDTO getCategoryByName(String name);

    //POST
    CategoryDTO createCategory(CategoryDTO categoryDTO);

    //PUT
    CategoryDTO updateCategory(Long id, CategoryDTO categoryDTO);

    //DELETE
    void deleteCategoryByID(Long id);

}

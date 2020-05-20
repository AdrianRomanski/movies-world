package adrianromanski.movies.services;


import adrianromanski.movies.model.CategoryDTO;

import java.util.List;

public interface CategoryService {

    List<CategoryDTO> getAllCategories();
}

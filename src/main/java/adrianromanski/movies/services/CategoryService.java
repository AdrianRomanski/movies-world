package adrianromanski.movies.services;


import adrianromanski.movies.model.CategoryDTO;
import adrianromanski.movies.model.MovieDTO;

import java.util.List;

public interface CategoryService {

    List<CategoryDTO> getAllCategories();

    List<MovieDTO> getAllMoviesForCategory(String name);

}

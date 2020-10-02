package adrianromanski.movies.services.category;

import adrianromanski.movies.aspects.creation_log.LogCreation;
import adrianromanski.movies.aspects.delete_log.LogDelete;
import adrianromanski.movies.aspects.paging_log.LogPaging;
import adrianromanski.movies.aspects.update_log.LogUpdate;
import adrianromanski.movies.domain.base_entity.Category;
import adrianromanski.movies.domain.base_entity.Movie;
import adrianromanski.movies.exceptions.ResourceNotFoundException;
import adrianromanski.movies.mapper.base_entity.CategoryMapper;
import adrianromanski.movies.mapper.base_entity.MovieMapper;
import adrianromanski.movies.model.base_entity.CategoryDTO;
import adrianromanski.movies.model.base_entity.MovieDTO;
import adrianromanski.movies.repositories.base_entity.CategoryRepository;
import adrianromanski.movies.repositories.pages.CategoryPageRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryPageRepository categoryPage;
    private final CategoryMapper categoryMapper;
    private final MovieMapper movieMapper;


    /**
     * User, Admin, Moderator
     * @return All Categories from database
     */
    @Override
    public List<CategoryDTO> getAllCategories() {
        return categoryRepository.findAll()
                .stream()
                .map(categoryMapper::categoryToCategoryDTO)
                .collect(Collectors.toList());
    }


    /**
     * User, Admin, Moderator
     * @return All Categories Paged
     */
    @Override
    @LogPaging
    public Page<Category> getAllCategoriesPaged(Pageable pageable) {
        return categoryPage.findAll(pageable);
    }


    /**
     * Converting All Categories in Page to CategoryDTO
     */
    @Override
    public Page<CategoryDTO> getPageCategoryDTO(Page<Category> categoryPage, Pageable pageable) {
        List<CategoryDTO> categoriesDTO = categoryPage.get()
                .map(categoryMapper::categoryToCategoryDTO)
                .collect(Collectors.toList());
        return new PageImpl<>(categoriesDTO, pageable, categoriesDTO.size());
    }


    /**
     * User, Admin, Moderator
     * @param name of the Category looking for
     * @return List of movies with that Category
     * @throws ResourceNotFoundException if there is no Category with this name
     */
    @Override
    public List<MovieDTO> getAllMoviesForCategory(String name) {
        Category category = categoryRepository.findByName(name)
                .orElseThrow(() -> new ResourceNotFoundException(name, Category.class));
        return category.getMovies()
                .stream()
                .map(movieMapper::movieToMovieDTO)
                .collect(Collectors.toList());
    }


    /**
     * User, Admin, Moderator
     * @param name of the Category looking for
     * @return Category
     * @throws ResourceNotFoundException if not found
     */
    @Override
    public CategoryDTO getCategoryDTOByName(String name) {
        return categoryRepository.findByName(name)
                .map(categoryMapper::categoryToCategoryDTO)
                .orElseThrow(() -> new ResourceNotFoundException(name, Movie.class));
    }


    @Override
    public Category getCategoryById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(id, Movie.class));
    }

    /**
     * Admin, Moderator
     * @param id of the Category looking for
     * @return Category
     * @throws ResourceNotFoundException if not found
     */
    @Override
    public CategoryDTO getCategoryDTOById(Long id) {
        return categoryRepository.findById(id)
                .map(categoryMapper::categoryToCategoryDTO)
                .orElseThrow(() -> new ResourceNotFoundException(id, Movie.class));
    }


    /**
     * Admin, Moderator
     * Saving Category to Database
     * @param categoryDTO Object
     * @return Category if successfully saved to database
     */
    @Override
    @LogCreation
    public CategoryDTO createCategory(CategoryDTO categoryDTO) {
        Category category = categoryMapper.categoryDTOToCategory(categoryDTO);
        categoryRepository.save(category);
        return categoryMapper.categoryToCategoryDTO(category);
    }


    /**
     * Admin, Moderator
     * @param id of the Category to update
     * @param categoryDTO Object
     * @return Category if successfully saved
     * @throws ResourceNotFoundException if not found
     */
    @Override
    @LogUpdate
    public CategoryDTO updateCategory(Long id, CategoryDTO categoryDTO) {
        categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(id, Category.class));
        Category updatedCategory = categoryMapper.categoryDTOToCategory(categoryDTO);
        updatedCategory.setId(id);
        categoryRepository.save(updatedCategory);
        return categoryMapper.categoryToCategoryDTO(updatedCategory);
    }


    /**
     * Admin, Moderator
     * @param id of the Category to delete
     * @throws ResourceNotFoundException if not found
     */
    @Override
    @LogDelete
    public void deleteCategoryByID(Long id) {
         categoryRepository.findById(id)
                  .orElseThrow(() -> new ResourceNotFoundException(id, Category.class));
         categoryRepository.deleteById(id);
    }
}


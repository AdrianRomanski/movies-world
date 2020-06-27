package adrianromanski.movies.services.category;

import adrianromanski.movies.domain.base_entity.Category;
import adrianromanski.movies.domain.base_entity.Movie;
import adrianromanski.movies.exceptions.ResourceNotFoundException;
import adrianromanski.movies.jms.JmsTextMessageService;
import adrianromanski.movies.mapper.base_entity.CategoryMapper;
import adrianromanski.movies.mapper.base_entity.MovieMapper;
import adrianromanski.movies.model.base_entity.CategoryDTO;
import adrianromanski.movies.model.base_entity.MovieDTO;
import adrianromanski.movies.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final JmsTextMessageService jmsTextMessageService;
    private final CategoryMapper categoryMapper;
    private final MovieMapper movieMapper;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository, JmsTextMessageService jmsTextMessageService,
                               CategoryMapper categoryMapper, MovieMapper movieMapper) {
        this.categoryRepository = categoryRepository;
        this.jmsTextMessageService = jmsTextMessageService;
        this.categoryMapper = categoryMapper;
        this.movieMapper = movieMapper;
    }


    /**
     * User, Admin, Moderator
     * @return All Categories from database
     */
    @Override
    public List<CategoryDTO> getAllCategories() {
        jmsTextMessageService.sendTextMessage("Listing Categories");
        return categoryRepository.findAll()
                .stream()
                .map(categoryMapper::categoryToCategoryDTO)
                .collect(Collectors.toList());
    }


    /**
     * User, Admin, Moderator
     * @param name of the Category looking for
     * @return List of movies with that Category
     * @throws ResourceNotFoundException if there is no Category with this name
     */
    @Override
    public List<MovieDTO> getAllMoviesForCategory(String name) {
        jmsTextMessageService.sendTextMessage("Listing all Movies for Category: " + name);
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
    public CategoryDTO getCategoryByName(String name) {
        jmsTextMessageService.sendTextMessage("Finding Category: " + name);
        return categoryRepository.findByName(name)
                .map(categoryMapper::categoryToCategoryDTO)
                .orElseThrow(() -> new ResourceNotFoundException(name, Movie.class));
    }


    /**
     * Admin, Moderator
     * Saving Category to Database
     * @param categoryDTO Object
     * @return Category if successfully saved to database
     */
    @Override
    public CategoryDTO createCategory(CategoryDTO categoryDTO) {
        jmsTextMessageService.sendTextMessage("Creating new Category: " + categoryDTO.getName());
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
    public void deleteCategoryByID(Long id) {
         categoryRepository.findById(id)
                  .orElseThrow(() -> new ResourceNotFoundException(id, Category.class));
         categoryRepository.deleteById(id);
    }
}


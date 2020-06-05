package adrianromanski.movies.services.category;

import adrianromanski.movies.domain.Category;
import adrianromanski.movies.exceptions.ResourceNotFoundException;
import adrianromanski.movies.jms.JmsTextMessageService;
import adrianromanski.movies.mapper.CategoryMapper;
import adrianromanski.movies.mapper.MovieMapper;
import adrianromanski.movies.model.CategoryDTO;
import adrianromanski.movies.model.MovieDTO;
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

    @Override
    public List<CategoryDTO> getAllCategories() {
        jmsTextMessageService.sendTextMessage("Listing Categories");
        return categoryRepository.findAll()
                .stream()
                .map(categoryMapper::categoryToCategoryDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<MovieDTO> getAllMoviesForCategory(String name) {
        Category category = categoryRepository.findByName(name)
                .orElseThrow(() -> new ResourceNotFoundException("Movie with name: " + name + " not found"));
        jmsTextMessageService.sendTextMessage("Listing all Movies for Category " + name);
        return category.getMovies()
                .stream()
                .map(movieMapper::movieToMovieDTO)
                .collect(Collectors.toList());
    }
}


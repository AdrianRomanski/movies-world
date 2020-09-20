package adrianromanski.movies.controllers.user;

import adrianromanski.movies.domain.base_entity.Category;
import adrianromanski.movies.domain.base_entity.Movie;
import adrianromanski.movies.model.base_entity.CategoryDTO;
import adrianromanski.movies.model.base_entity.MovieDTO;
import adrianromanski.movies.services.category.CategoryService;
import adrianromanski.movies.services.movie.MovieService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


@Controller
@AllArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;
    private final MovieService movieService;


    @GetMapping("/categories/page/{page}")
    public ModelAndView getAllCategoriesPaged(@PathVariable int page) {
        ModelAndView modelAndView = new ModelAndView("user/category/showCategories");
        PageRequest pageable = PageRequest.of(page - 1, 8);
        Page<Category> categoryPage = categoryService.getAllCategoriesPaged(pageable);
        Page<CategoryDTO> categoryDTOPage = categoryService.getPageCategoryDTO(categoryPage, pageable);
        int totalPages = categoryPage.getTotalPages();
        if(totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1,totalPages).boxed().collect(Collectors.toList());
            modelAndView.addObject("pageNumbers", pageNumbers);
        }
        modelAndView.addObject("activeCategoryList", true);
        modelAndView.addObject("categoriesDTOList", categoryDTOPage.getContent());
        return modelAndView;
    }

    @GetMapping(value = "/categories/{categoryName}/page/{page}")
    public ModelAndView getAllMoviesForCategoryPaged(@PathVariable int page, @PathVariable String categoryName) {
        ModelAndView modelAndView = new ModelAndView("user/category/categoryMovies");
        PageRequest pageable = PageRequest.of(page - 1, 8);
        Page<Movie> moviePage = movieService.getAllMoviesForCategoryPaged(categoryName, pageable);
        Page<MovieDTO> movieDTOPage = movieService.getPageMovieDTO(moviePage, pageable);
        int totalPages = moviePage.getTotalPages();
        if(totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1,totalPages).boxed().collect(Collectors.toList());
            modelAndView.addObject("pageNumbers", pageNumbers);
        }
        modelAndView.addObject("categoryDTO", categoryService.getCategoryDTOByName(categoryName));
        modelAndView.addObject("activeMovieList", true);
        modelAndView.addObject("moviesDTOList", movieDTOPage.getContent());
        return modelAndView;
    }

}

package adrianromanski.movies.controllers.user;

import adrianromanski.movies.services.category.CategoryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@Controller
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("category/{categoryName}")
    public String getAllMoviesForCategory(@PathVariable String categoryName, Model model) {
        model.addAttribute("movies", categoryService.getAllMoviesForCategory(categoryName));
        return "category";
    }

}

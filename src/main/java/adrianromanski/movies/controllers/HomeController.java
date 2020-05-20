package adrianromanski.movies.controllers;

import adrianromanski.movies.services.CategoryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    private final CategoryService categoryService;

    public HomeController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/")
    public String home(Model model) {

        System.out.println(categoryService.getAllCategories().size());

        model.addAttribute("Categories", categoryService.getAllCategories());
        return "home";
    }
}

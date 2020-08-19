package adrianromanski.movies.controllers.admin;

import adrianromanski.movies.model.base_entity.CategoryDTO;
import adrianromanski.movies.services.category.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Slf4j
@Controller
public class AdminCategoryController {

    private final CategoryService categoryService;

    public AdminCategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("admin/createCategory")
    public String createCategory(Model model) {
        model.addAttribute("categories", categoryService.getAllCategories());
        model.addAttribute("categoryDTO", new CategoryDTO());

        return "admin/createCategoryForm";
    }

    @GetMapping("admin/showCategories")
    public String showCategories(Model model) {
        model.addAttribute("showCategories", categoryService.getAllCategories());
        return "admin/showCategories";
    }


    @PostMapping("admin/checkCategory")
    public String checkCategoryCreation(@Valid @ModelAttribute("categoryDTO") CategoryDTO categoryDTO,
                                    BindingResult bindingResult,
                                    Model model)  {
        model.addAttribute("categories", categoryService.getAllCategories());
        if(bindingResult.hasErrors()){
            bindingResult.getAllErrors().forEach(objectError -> {
                log.warn(objectError.getDefaultMessage());
            });
            return "admin/createCategoryForm";
        }
        categoryService.createCategory(categoryDTO);
        model.addAttribute("showCategories", categoryService.getAllCategories());
        return "admin/showCategories";
    }
}

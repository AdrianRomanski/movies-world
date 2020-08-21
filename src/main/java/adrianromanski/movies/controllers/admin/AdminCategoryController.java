package adrianromanski.movies.controllers.admin;

import adrianromanski.movies.model.base_entity.CategoryDTO;
import adrianromanski.movies.services.category.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@Controller
public class AdminCategoryController {

    private final CategoryService categoryService;

    public AdminCategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }


    @GetMapping("admin/showCategories")
    public String showCategories(Model model) {
        model.addAttribute("showCategories", categoryService.getAllCategories());
        return "admin/category/showCategories";
    }


    @GetMapping("admin/createCategory")
    public String createCategory(Model model) {
        model.addAttribute("categories", categoryService.getAllCategories());
        model.addAttribute("categoryDTO", new CategoryDTO());

        return "admin/category/createCategoryForm";
    }


    @PostMapping("admin/createCategory/check")
    public String checkCategoryCreation(@Valid @ModelAttribute("categoryDTO") CategoryDTO categoryDTO,
                                        BindingResult bindingResult,
                                        Model model)  {
        model.addAttribute("categories", categoryService.getAllCategories());
        if(bindingResult.hasErrors()){
            bindingResult.getAllErrors()
                    .forEach(objectError -> log.warn(objectError.getDefaultMessage()));
            return "admin/category/createCategoryForm";
        }
        categoryService.createCategory(categoryDTO);
        model.addAttribute("showCategories", categoryService.getAllCategories());
        return "admin/category/showCategories";
    }


    @GetMapping("admin/updateCategory/{id}")
    public String updateCategory(Model model, @PathVariable String id) {
        model.addAttribute("categoryDTO", categoryService.getCategoryById(Long.valueOf(id)));
        return "admin/category/updateCategoryForm";
    }


    @PostMapping("admin/updateCategory/check")
    public String checkCategoryUpdate(@Valid @ModelAttribute("categoryDTO") CategoryDTO categoryDTO,
                                      BindingResult bindingResult,
                                      Model model)  {
        if(bindingResult.hasErrors()){
            bindingResult.getAllErrors().forEach(objectError -> {
                log.warn(objectError.getDefaultMessage());
            });
            return "admin/category/updateCategoryForm";
        }
        categoryService.updateCategory(categoryDTO.getId(), categoryDTO);
        model.addAttribute("showCategories", categoryService.getAllCategories());
        return "admin/category/showCategories";
    }


    @GetMapping("admin/deleteCategory/{id}")
    public String deleteCategory(@PathVariable String id, Model model) {
        categoryService.deleteCategoryByID(Long.valueOf(id));
        model.addAttribute("showCategories", categoryService.getAllCategories());
        return "admin/category/showCategories";
    }
}

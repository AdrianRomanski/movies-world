package adrianromanski.movies.controllers.admin;

import adrianromanski.movies.domain.base_entity.Category;
import adrianromanski.movies.model.base_entity.CategoryDTO;
import adrianromanski.movies.services.category.CategoryService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Slf4j
@Controller
@AllArgsConstructor
public class AdminCategoryController {

    private final CategoryService categoryService;

    @RequestMapping(value = "/admin/category/showCategories/page/{page}")
    public ModelAndView listCategoryPageByPage(@PathVariable("page") int page) {
        ModelAndView modelAndView = new ModelAndView("admin/category/showCategories");
        PageRequest pageable = PageRequest.of(page - 1, 5);
        Page<Category> categoryPages = categoryService.getAllCategoriesPaged(pageable);
        Page<CategoryDTO> categoryDTOPage = categoryService.getPageCategoryDTO(categoryPages, pageable);
        int totalPages = categoryPages.getTotalPages();
        if(totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1,totalPages).boxed().collect(Collectors.toList());
            modelAndView.addObject("pageNumbers", pageNumbers);
        }
        modelAndView.addObject("activeCategoryList", true);
        modelAndView.addObject("categoryList", categoryDTOPage.getContent());
        return modelAndView;
    }


    @GetMapping("admin/category/createCategory")
    public String createCategory(Model model) {
        model.addAttribute("categories", categoryService.getAllCategories());
        model.addAttribute("categoryDTO", new CategoryDTO());

        return "admin/category/createCategoryForm";
    }


    @PostMapping("admin/category/createCategory/check")
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


    @GetMapping("admin/category/updateCategory/{id}")
    public String updateCategory(Model model, @PathVariable String id) {
        model.addAttribute("categoryDTO", categoryService.getCategoryDTOById(Long.valueOf(id)));
        return "admin/category/updateCategoryForm";
    }


    @PostMapping("admin/category/updateCategory/check")
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


    @GetMapping("admin/category/deleteCategory/{id}")
    public String deleteCategory(@PathVariable String id, Model model) {
        categoryService.deleteCategoryByID(Long.valueOf(id));
        model.addAttribute("showCategories", categoryService.getAllCategories());
        return "admin/category/showCategories";
    }
}

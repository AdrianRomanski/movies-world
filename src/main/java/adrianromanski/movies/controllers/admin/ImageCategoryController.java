package adrianromanski.movies.controllers.admin;

import adrianromanski.movies.services.category.CategoryService;
import adrianromanski.movies.services.image.ImageServiceCategory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import static com.google.common.io.ByteStreams.copy;

@Controller
public class ImageCategoryController {

    private final ImageServiceCategory imageService;
    private final CategoryService categoryService;

    public ImageCategoryController(ImageServiceCategory imageService, CategoryService categoryService) {
        this.imageService = imageService;
        this.categoryService = categoryService;

    }

    @GetMapping("category/{name}/image")
    public String showUploadForm(@PathVariable String name, Model model) {
        model.addAttribute("category", categoryService.getCategoryByName(name));

        return "admin/category/categoryImageUplForm";
    }

    @PostMapping("category/{name}/image")
    public String handleImagePost(@PathVariable String name, @RequestParam("imagefile") MultipartFile file) throws IOException {
        var category = categoryService.getCategoryByName(name);
        imageService.saveImageFile(category, file);

        return "redirect:/category/" + name;
    }

    @GetMapping("category/{name}/categoryImage")
    public void renderImageFromDB(@PathVariable String name, HttpServletResponse response) throws IOException {
        var categoryDTO = categoryService.getCategoryByName(name);
        if (categoryDTO.getImage() != null) {
            byte[] byteArray = new byte[categoryDTO.getImage().length];
            int i = 0;

            for (Byte wrappedByte : categoryDTO.getImage()) {
                byteArray[i++] = wrappedByte; //auto unboxing
            }
            response.setContentType("image/jpeg");
            InputStream is = new ByteArrayInputStream(byteArray);
            copy(is, response.getOutputStream());
        }
    }
}

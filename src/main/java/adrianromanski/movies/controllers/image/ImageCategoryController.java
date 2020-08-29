package adrianromanski.movies.controllers.image;

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
        model.addAttribute("category", categoryService.getCategoryDTOByName(name));
        return "admin/category/categoryImageUplForm";
    }

    @PostMapping("category/{id}/image")
    public String handleImagePost(@PathVariable String id, @RequestParam("imagefile") MultipartFile file) throws IOException {
        var category = categoryService.getCategoryById(Long.valueOf(id));
        imageService.saveImageFile(category, file);
        return "redirect:/category/" + category.getName();
    }

    @GetMapping("category/{id}/categoryImage")
    public void renderImageFromDB(@PathVariable String id, HttpServletResponse response) throws IOException {
        var category = categoryService.getCategoryById(Long.valueOf(id));
        if (category.getImage() != null) {
            byte[] byteArray = new byte[category.getImage().length];
            int i = 0;

            for (Byte wrappedByte : category.getImage()) {
                byteArray[i++] = wrappedByte; //auto unboxing
            }
            response.setContentType("image/jpeg");
            InputStream is = new ByteArrayInputStream(byteArray);
            copy(is, response.getOutputStream());
        }
    }
}

package adrianromanski.movies.controllers.admin;

import adrianromanski.movies.services.image.ImageServiceMovie;
import adrianromanski.movies.services.movie.MovieService;
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
public class ImageMovieController {

    private final ImageServiceMovie imageService;
    private final MovieService movieService;

    public ImageMovieController(ImageServiceMovie imageService, MovieService movieService) {
        this.imageService = imageService;
        this.movieService = movieService;
    }

    @GetMapping("movie/{name}/image")
    public String showUploadForm(@PathVariable String name, Model model) {
        model.addAttribute("movie", movieService.getMovieByName(name));

        // I have to create form for movie image upload later
        return "admin/category/categoryImageUplForm";
    }

    @PostMapping("movie/{name}/image")
    public String handleImagePost(@PathVariable String name, @RequestParam("imagefile") MultipartFile file) throws IOException {
        var movie = movieService.getMovieByName(name);
        imageService.saveImageFile(movie, file);

        return "admin/movie/showMoviesForm";
    }


    @GetMapping("movie/{id}/movieImage")
    public void renderImageFromDB(@PathVariable String id, HttpServletResponse response) throws IOException {
        var movieDTO = movieService.getMovieByID(Long.valueOf(id));
        if (movieDTO.getImage() != null) {
            byte[] byteArray = new byte[movieDTO.getImage().length];
            int i = 0;

            for (Byte wrappedByte : movieDTO.getImage()) {
                byteArray[i++] = wrappedByte; //auto unboxing
            }
            response.setContentType("image/jpeg");
            InputStream is = new ByteArrayInputStream(byteArray);
            copy(is, response.getOutputStream());
        }
    }
}

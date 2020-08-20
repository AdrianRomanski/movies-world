package adrianromanski.movies.controllers.admin;

import adrianromanski.movies.services.image.ImageServiceMovie;
import adrianromanski.movies.services.movie.MovieService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

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


    @GetMapping("movie/{name}/movieImage")
    public void renderImageFromDB(@PathVariable String name, HttpServletResponse response) throws IOException {
        var movieDTO = movieService.getMovieByName(name);
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

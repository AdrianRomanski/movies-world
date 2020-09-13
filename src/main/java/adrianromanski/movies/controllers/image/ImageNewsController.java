package adrianromanski.movies.controllers.image;

import adrianromanski.movies.services.news.NewsServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import static org.apache.tomcat.util.http.fileupload.IOUtils.copy;

@Controller
@AllArgsConstructor
public class ImageNewsController {

    private final NewsServiceImpl newsService;

    @GetMapping("news/{id}/newsImage")
    public void renderImageFromDB(@PathVariable String id, HttpServletResponse response) throws IOException {
        var event = newsService.getNewsByID(Long.valueOf(id));
        if (event.getImage() != null) {
            byte[] byteArray = new byte[event.getImage().length];
            int i = 0;

            for (Byte wrappedByte : event.getImage()) {
                byteArray[i++] = wrappedByte;
            }
            response.setContentType("image/jpeg");
            InputStream is = new ByteArrayInputStream(byteArray);
            copy(is, response.getOutputStream());
        }
    }
}

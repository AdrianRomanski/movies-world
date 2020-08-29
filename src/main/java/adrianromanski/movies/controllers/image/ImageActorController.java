package adrianromanski.movies.controllers.image;

import adrianromanski.movies.services.actor.ActorServiceImpl;
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
public class ImageActorController {

    private final ActorServiceImpl actorService;


    @GetMapping("actor/{id}/actorImage")
    public void renderImageFromDB(@PathVariable String id, HttpServletResponse response) throws IOException {
        var actorDTO = actorService.getActorByID(Long.valueOf(id));
        if (actorDTO.getImage() != null) {
            byte[] byteArray = new byte[actorDTO.getImage().length];
            int i = 0;

            for (Byte wrappedByte : actorDTO.getImage()) {
                byteArray[i++] = wrappedByte; //auto unboxing
            }
            response.setContentType("image/jpeg");
            InputStream is = new ByteArrayInputStream(byteArray);
            copy(is, response.getOutputStream());
        }
    }
}

package adrianromanski.movies.controllers.image;

import adrianromanski.movies.services.actor.ActorServiceImpl;
import adrianromanski.movies.services.image.ImageServiceActor;
import lombok.AllArgsConstructor;
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

import static org.apache.tomcat.util.http.fileupload.IOUtils.copy;

@Controller
@AllArgsConstructor
public class ImageActorController {

    private final ActorServiceImpl actorService;
    private final ImageServiceActor imageService;

    @GetMapping("actor/{id}/actorImage")
    public void renderImageFromDB(@PathVariable String id, HttpServletResponse response) throws IOException {
        var actorDTO = actorService.getActorByID(Long.valueOf(id));
        if (actorDTO.getImage() != null) {
            byte[] byteArray = new byte[actorDTO.getImage().length];
            int i = 0;

            for (Byte wrappedByte : actorDTO.getImage()) {
                byteArray[i++] = wrappedByte;
            }
            response.setContentType("image/jpeg");
            InputStream is = new ByteArrayInputStream(byteArray);
            copy(is, response.getOutputStream());
        }
    }

    @GetMapping("actor/{id}/image")
    public String showUploadForm(@PathVariable String id, Model model) {
        model.addAttribute("actorDTO", actorService.getActorByID(Long.valueOf(id)));
        return "admin/actor/actorImageUplForm";
    }

    @PostMapping("actor/{id}/image")
    public String handleImagePost(@PathVariable String id, Model model,
                                  @RequestParam("imagefile") MultipartFile file) throws IOException {
        var actorDTO = actorService.getActorByID(Long.valueOf(id));
        imageService.saveImageFile(actorDTO, file);
        model.addAttribute("actorDTO", actorDTO);
        return "admin/actor/showActorForm";
    }
}


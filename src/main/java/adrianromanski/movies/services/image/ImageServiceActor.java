package adrianromanski.movies.services.image;

import adrianromanski.movies.mapper.person.ActorMapper;
import adrianromanski.movies.model.person.ActorDTO;
import adrianromanski.movies.repositories.person.ActorRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@AllArgsConstructor
public class ImageServiceActor implements ImageService<ActorDTO>{

    private final ActorRepository actorRepository;
    private final ActorMapper actorMapper;

    @Override
    public void saveImageFile(ActorDTO actorDTO, MultipartFile file) throws IOException {
        var actor = actorMapper.actorDTOToActor(actorDTO);
        var byteObjects = new Byte[file.getBytes().length];
        var i = 0;

        for(byte b: file.getBytes()) {
            byteObjects[i++] = b;
        }
        actor.setImage(byteObjects);
        actorRepository.save(actor);
    }
}

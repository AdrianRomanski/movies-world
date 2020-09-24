package adrianromanski.movies.services.image;

import adrianromanski.movies.domain.person.Actor;
import adrianromanski.movies.mapper.person.ActorMapper;
import adrianromanski.movies.mapper.person.ActorMapperImpl;
import adrianromanski.movies.model.person.ActorDTO;
import adrianromanski.movies.repositories.person.ActorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class ImageServiceActorTest {

    @Mock
    ActorRepository actorRepository;

    ImageServiceActor imageService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);

        ActorMapper actorMapper = new ActorMapperImpl();

        imageService = new ImageServiceActor(actorRepository, actorMapper);
    }


    @Test
    @DisplayName("Happy Path, method = saveImageFile")
    void saveImageFile() throws IOException {
        //given
        Long id = 1L;
        MultipartFile multipartFile = new MockMultipartFile("imagefile", "testing.txt", "text/plain",
                "File".getBytes());

        Actor actor = new Actor();
        actor.setId(id);
        Optional<Actor> movieOptional = Optional.of(actor);

        ActorDTO actorDTO = new ActorDTO();

        when(actorRepository.findById(anyLong())).thenReturn(movieOptional);

        ArgumentCaptor<Actor> argumentCaptor = ArgumentCaptor.forClass(Actor.class);

        //when
        imageService.saveImageFile(actorDTO, multipartFile);

        //then
        verify(actorRepository, times(1)).save(argumentCaptor.capture());

        Actor savedActor = argumentCaptor.getValue();
        assertEquals(multipartFile.getBytes().length, savedActor.getImage().length);
    }
}

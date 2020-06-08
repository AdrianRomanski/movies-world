package adrianromanski.movies.services;

import adrianromanski.movies.domain.Actor;
import adrianromanski.movies.exceptions.ResourceNotFoundException;
import adrianromanski.movies.mapper.ActorMapper;
import adrianromanski.movies.mapper.ActorMapperImpl;
import adrianromanski.movies.model.ActorDTO;
import adrianromanski.movies.repositories.ActorRepository;
import adrianromanski.movies.services.actor.ActorService;
import adrianromanski.movies.services.actor.ActorServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class ActorServiceImplTest {

    public static final String FIRST_NAME = "Steven";
    public static final String LAST_NAME = "Spielberg";
    @Mock
    ActorRepository actorRepository;

    ActorService actorService;

    private ActorDTO getActorDTO() { return ActorDTO.builder().firstName(FIRST_NAME).lastName(LAST_NAME).build(); }
    private Actor getActor() { return Actor.builder().firstName(FIRST_NAME).lastName(LAST_NAME).build(); }

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        ActorMapper actorMapper = new ActorMapperImpl();

        actorService = new ActorServiceImpl(actorRepository, actorMapper);
    }


    @DisplayName("Happy Path, method = getAllActors")
    @Test
    void getAllActors() {
        List<Actor> actors = Arrays.asList(new Actor(), new Actor(), new Actor());

        when(actorRepository.findAll()).thenReturn(actors);

        List<ActorDTO> returnDTO = actorService.getAllActors();

        assertEquals(returnDTO.size(), 3);
    }


    @DisplayName("Happy Path, method = createActor")
    @Test
    void createActor() {
        ActorDTO actorDTO = getActorDTO();

        ActorDTO returnDTO = actorService.createActor(actorDTO);

        assertEquals(returnDTO.getFirstName(), FIRST_NAME);
        assertEquals(returnDTO.getLastName(), LAST_NAME);
    }


    @DisplayName("Happy Path, method = updateActor")
    @Test
    void updateActorHappyPath() {
        ActorDTO actorDTO = getActorDTO();
        actorDTO.setFirstName("Updating");
        Actor actor = getActor();

        when(actorRepository.findById(anyLong())).thenReturn(Optional.of(actor));

        ActorDTO returnDTO = actorService.updateActor(1L, actorDTO);

        assertEquals(returnDTO.getFirstName(), "Updating");
        assertEquals(returnDTO.getLastName(), LAST_NAME);
    }


    @DisplayName("UnHappy Path, method = updateActor")
    @Test
    void updateActorUnHappyPath() {
        ActorDTO actorDTO = getActorDTO();
        Throwable ex = catchThrowable(() -> actorService.updateActor(1L, actorDTO));

        assertThat(ex).isInstanceOf(ResourceNotFoundException.class);
    }


    @DisplayName("Happy Path, method = deleteActorByID")
    @Test
    void deleteActorHappyPath() {
        Actor actor = getActor();

        when(actorRepository.findById(anyLong())).thenReturn(Optional.of(actor));

        actorService.deleteActorByID(1L);

        verify(actorRepository, times(1)).deleteById(anyLong());
    }


    @DisplayName("UnHappy Path, method = deleteActorByID")
    @Test
    void deleteActorUnHappyPath() {
        Throwable ex = catchThrowable(() -> actorService.deleteActorByID(1L));

        assertThat(ex).isInstanceOf(ResourceNotFoundException.class);

        verify(actorRepository, times(0)).deleteById(anyLong());
    }
}
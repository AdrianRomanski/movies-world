package adrianromanski.movies.services.person;

import adrianromanski.movies.domain.award.ActorAward;
import adrianromanski.movies.domain.person.Actor;
import adrianromanski.movies.exceptions.ResourceNotFoundException;
import adrianromanski.movies.mapper.award.ActorAwardMapper;
import adrianromanski.movies.mapper.award.ActorAwardMapperImpl;
import adrianromanski.movies.mapper.person.ActorMapper;
import adrianromanski.movies.mapper.person.ActorMapperImpl;
import adrianromanski.movies.model.award.ActorAwardDTO;
import adrianromanski.movies.model.person.ActorDTO;
import adrianromanski.movies.repositories.award.AwardRepository;
import adrianromanski.movies.repositories.pages.ActorPageRepository;
import adrianromanski.movies.repositories.person.ActorRepository;
import adrianromanski.movies.services.actor.ActorService;
import adrianromanski.movies.services.actor.ActorServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class ActorServiceImplTest {

    public static final String FIRST_NAME = "Steven";
    public static final String LAST_NAME = "Spielberg";
    public static final String COUNTRY = "Poland";
    public static final String CATEGORY = "Best Actor";

    @Mock
    ActorRepository actorRepository;

    @Mock
    AwardRepository awardRepository;

    @Mock
    ActorPageRepository actorPageRepository;



    ActorService actorService;

    private ActorDTO getActorDTO() { return ActorDTO.builder().firstName(FIRST_NAME).lastName(LAST_NAME).build(); }

    private Actor getActor() {
        ActorAward award = ActorAward.builder().country(COUNTRY).awardCategory(CATEGORY).build();
        Actor actor = Actor.builder().firstName(FIRST_NAME).lastName(LAST_NAME).awards(Collections.singletonList(award)).build();
        award.setActor(actor);
        return actor;
    }

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        ActorMapper actorMapper = new ActorMapperImpl();
        ActorAwardMapper awardMapper = new ActorAwardMapperImpl();

        actorService = new ActorServiceImpl(actorRepository, awardRepository, actorPageRepository, actorMapper, awardMapper);
    }


    @DisplayName("Happy Path, method = getAllActors")
    @Test
    void getAllActors() {
        List<Actor> actors = Arrays.asList(new Actor(), new Actor(), new Actor());

        when(actorRepository.findAll()).thenReturn(actors);

        List<ActorDTO> returnDTO = actorService.getAllActors();

        assertEquals(returnDTO.size(), 3);
    }


    @DisplayName("Happy Path, method = getActorById")
    @Test
    void getActorById() {
        Actor actor = Actor.builder().id(1L).build();

        when(actorRepository.findById(anyLong())).thenReturn(Optional.of(actor));

        ActorDTO returnDTO = actorService.getActorByID(1L);

        assertEquals(returnDTO.getId(), 1);
    }


    @DisplayName("Happy Path, method = getAllActorsPaged")
    @Test
    void getAllActorsPaged() {
        List<Actor> actors = Arrays.asList(new Actor(), new Actor(), new Actor());

        PageRequest pageable = PageRequest.of(0, 3);

        Page<Actor> actorPage = new PageImpl<>(actors, pageable, actors.size());

        when(actorPageRepository.findAll(pageable)).thenReturn(actorPage);

        Page<Actor> returnPage = actorService.getActorsPaged(pageable);

        assertEquals(returnPage.getTotalElements(), 3);
        assertEquals(returnPage.getTotalPages(), 1);
    }


    @DisplayName("Happy Path, method = createActor")
    @Test
    void createActor() {
        ActorDTO actorDTO = getActorDTO();

        ActorDTO returnDTO = actorService.createActor(actorDTO);

        assertEquals(returnDTO.getFirstName(), FIRST_NAME);
        assertEquals(returnDTO.getLastName(), LAST_NAME);
    }


    @DisplayName("Happy Path, method = addAward")
    @Test
    void addAwardHappyPath() {
        Actor actor = new Actor();
        ActorAwardDTO awardDTO = new ActorAwardDTO();
        awardDTO.setCountry(COUNTRY);

        when(actorRepository.findById(anyLong())).thenReturn(Optional.of(actor));

        ActorAwardDTO returnDTO = actorService.addAward(1L, awardDTO);

        assertEquals(returnDTO.getCountry(), COUNTRY);
    }


    @DisplayName("UnHappy Path, method = addAward")
    @Test
    void addAwardUnHappyPath() {
        ActorAwardDTO awardDTO = new ActorAwardDTO();
        Throwable ex = catchThrowable(() -> actorService.addAward(1L, awardDTO));

        assertThat(ex).isInstanceOf(ResourceNotFoundException.class);
    }


    @DisplayName("Happy Path, method = updateActor")
    @Test
    void updateActorHappyPath() {
        ActorDTO actorDTO = new ActorDTO();
        actorDTO.setFirstName("Updating");
        Actor actor = new Actor();
        ActorAward award = new ActorAward();
        award.setId(1L);
        actor.getAwards().add(award);
        award.setActor(actor);

        when(actorRepository.findById(anyLong())).thenReturn(Optional.of(actor));

        ActorDTO returnDTO = actorService.updateActor(1L, actorDTO);

        assertEquals(returnDTO.getFirstName(), "Updating");
    }


    @DisplayName("UnHappy Path, method = updateActor")
    @Test
    void updateActorUnHappyPath() {
        ActorDTO actorDTO = getActorDTO();
        Throwable ex = catchThrowable(() -> actorService.updateActor(1L, actorDTO));

        assertThat(ex).isInstanceOf(ResourceNotFoundException.class);
    }


    @DisplayName("Happy Path, method = updateAward")
    @Test
    void updateAwardHappyPath() {
        Actor actor = new Actor();
        ActorAward award = new ActorAward();
        award.setId(1L);
        actor.getAwards().add(award);
        award.setActor(actor);
        ActorAwardDTO awardDTO = new ActorAwardDTO();
        awardDTO.setCountry(COUNTRY);

        when(actorRepository.findById(anyLong())).thenReturn(Optional.of(actor));

        ActorAwardDTO returnDTO = actorService.updateAward(1L, 1L, awardDTO);

        assertEquals(returnDTO.getCountry(), COUNTRY);
    }

    @DisplayName("UnHappy Path, method = updateAward")
    @Test
    void updateAwardUnHappyPath() {
        ActorAwardDTO awardDTO = new ActorAwardDTO();
        Throwable ex = catchThrowable(() -> actorService.updateAward(1L, 1L, awardDTO));

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


    @DisplayName("Happy Path, method = deleteAward")
    @Test
    void deleteAwardHappyPath() {
        Actor actor = new Actor();
        ActorAward award = new ActorAward();
        award.setId(1L);
        actor.getAwards().add(award);
        award.setActor(actor);

        when(actorRepository.findById(anyLong())).thenReturn(Optional.of(actor));

        actorService.deleteAward(1L, 1L);

        verify(awardRepository, times(1)).deleteById(anyLong());
    }


    @DisplayName("UnHappy Path, method = deleteAward")
    @Test
    void deleteAwardUnHappyPath() {
        Throwable ex = catchThrowable(() -> actorService.deleteAward(1L, 1L));

        assertThat(ex).isInstanceOf(ResourceNotFoundException.class);

        verify(awardRepository, times(0)).deleteById(anyLong());
    }

}
package adrianromanski.movies.controllers.user;

import adrianromanski.movies.domain.person.Actor;
import adrianromanski.movies.model.base_entity.MovieDTO;
import adrianromanski.movies.model.person.ActorDTO;
import adrianromanski.movies.services.actor.ActorServiceImpl;
import adrianromanski.movies.services.movie.MovieServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


class ActorControllerTest {

    @Mock
    ActorServiceImpl actorService;

    @Mock
    MovieServiceImpl movieService;

    @InjectMocks
    ActorController actorController;

    MockMvc mockMvc;

    List<Actor> actors;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);

        mockMvc = MockMvcBuilders.standaloneSetup(actorController).build();

        actors = Arrays.asList(new Actor(), new Actor(), new Actor());
    }

    @Test
    @DisplayName("GET, method = showActor")
    void getActor() throws Exception {
        ActorDTO actorDTO = ActorDTO.builder().id(1L).build();
        List<MovieDTO> movieDTOS = Arrays.asList(new MovieDTO(), new MovieDTO());

        when(actorService.getActorByID(anyLong())).thenReturn(actorDTO);
        when(movieService.findAllMoviesWithActor(anyLong())).thenReturn(movieDTOS);

        mockMvc.perform(get("/actor/1"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("actor"))
                .andExpect(model().attributeExists("actorMovies"))
                .andExpect(view().name("user/actors/showActor"));
    }

    @Test
    @DisplayName("GET, method = getActors")
    void getActors() throws Exception {
        // Given
        List<Actor> actorList = Arrays.asList(new Actor(), new Actor(), new Actor());
        PageRequest pageable = PageRequest.of(0, 8);
        Page<Actor> actorPage = new PageImpl<>(actorList, pageable, actorList.size());

        //When
        when(actorService.getActorsPaged( pageable)).thenReturn(actorPage);

        //Then
        mockMvc.perform(get("/actors/page/1"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("actorsList"))
                .andExpect(view().name("user/actors/showActors"));
    }


    @Test
    @DisplayName("GET, method = getActorMovies")
    void getActorMovies() throws Exception {
        // Given
        List<MovieDTO> movieList = Arrays.asList(new MovieDTO(), new MovieDTO(), new MovieDTO());

        //When
        when(actorService.getAllMoviesForActor(anyLong())).thenReturn(movieList);

        //Then
        mockMvc.perform(get("/actor/1/movies"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("moviesDTOList"))
                .andExpect(view().name("user/movies/showMovies"));
    }

    @Test
    @DisplayName("GET, Happy Path, method = showActorsSortedDesc")
    void showActorsSortedDesc() throws Exception {
        // Given
        PageRequest pageable = PageRequest.of(0, 8, Sort.by("lastName").descending());
        Page<Actor> actorPage = new PageImpl<>(actors, pageable, actors.size());

        when(actorService.getActorsPaged(pageable)).thenReturn(actorPage);

        mockMvc.perform(get("/actors/sorted/desc/lastName/page/1"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("activeActorsList"))
                .andExpect(model().attributeExists("actorsList"))
                .andExpect(model().attributeExists("pageNumbers"))
                .andExpect(view().name("user/actors/actorsSorted"));
    }

    @Test
    @DisplayName("GET, Happy Path, method = showActorsSortedAsc")
    void showActorsSortedAsc() throws Exception {
        // Given
        PageRequest pageable = PageRequest.of(0, 8, Sort.by("lastName").ascending());
        Page<Actor> actorPage = new PageImpl<>(actors, pageable, actors.size());

        when(actorService.getActorsPaged(pageable)).thenReturn(actorPage);

        mockMvc.perform(get("/actors/sorted/asc/lastName/page/1"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("activeActorsList"))
                .andExpect(model().attributeExists("actorsList"))
                .andExpect(model().attributeExists("pageNumbers"))
                .andExpect(view().name("user/actors/actorsSorted"));
    }
}
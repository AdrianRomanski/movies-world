package adrianromanski.movies.controllers.admin;

import adrianromanski.movies.domain.base_entity.Movie;
import adrianromanski.movies.domain.person.Actor;
import adrianromanski.movies.model.base_entity.MovieDTO;
import adrianromanski.movies.model.person.ActorDTO;
import adrianromanski.movies.services.actor.ActorService;
import adrianromanski.movies.services.movie.MovieService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class AdminActorControllerTest {

    @Mock
    ActorService actorService;

    @Mock
    MovieService movieService;

    @InjectMocks
    AdminActorController controller;

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);

        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    @DisplayName("GET, method = showActorsPaged")
    void showActorsPaged() throws Exception {
        //given
        List<Actor> actorList = Arrays.asList(new Actor(), new Actor(), new Actor());

        PageRequest pageable = PageRequest.of(0, 12);

        Page<Actor> actorPageable = new PageImpl<>(actorList, pageable, actorList.size());
        //when
        when(actorService.getActorsPaged(pageable)).thenReturn(actorPageable);

        //then
        mockMvc.perform(get("/admin/actor/showActors/page/1"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("actorsList"))
                .andExpect(view().name("admin/actor/showActorsForm"));
    }


    @Test
    @DisplayName("GET, method = createActor")
    void createActor() throws Exception {
        mockMvc.perform(get("/admin/actor/createActor"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("actorDTO"))
                .andExpect(view().name("admin/actor/createActorForm"));
    }

    @Test
    @DisplayName("GET, method = showActor")
    void showActor() throws Exception {
        ActorDTO actorDTO = new ActorDTO();
        List<MovieDTO> movieDTOS = Arrays.asList(new MovieDTO(), new MovieDTO());

        when(movieService.findAllMoviesWithActor(anyLong())).thenReturn(movieDTOS);
        when(actorService.getActorByID(anyLong())).thenReturn(actorDTO);

        mockMvc.perform(get("/admin/actor/showActor/1"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("actorDTO"))
                .andExpect(model().attributeExists("movies"))
                .andExpect(view().name("admin/actor/showActorForm"));
    }


    @Test
    @DisplayName("GET, method = checkActorCreation")
    void checkActorCreationHappyPath() throws Exception {
        //given
        ActorDTO actorDTO = new ActorDTO();

        //when
        when(actorService.createActor(any(ActorDTO.class))).thenReturn(actorDTO);

        mockMvc.perform(post("/admin/actor/createActor/check")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("firstName", "Adrian")
                .param("lastName", "Romanski")
                .param("country", "Poland")
                .param("dateOfBirth", String.valueOf(LocalDate.of(1944, 3, 4)))
        )
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("actorDTO"))
                .andExpect(view().name("admin/actor/actorImageUplForm"));
    }

    @Test
    @DisplayName("Unhappy Path, GET, method = checkActorCreation")
    void checkActorCreationUnHappy() throws Exception {
        //given
        ActorDTO actorDTO = new ActorDTO();

        //when
        when(actorService.createActor(any(ActorDTO.class))).thenReturn(actorDTO);

        mockMvc.perform(post("/admin/actor/createActor/check")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("firstName", "a")
                .param("lastName", "r")
                .param("country", "123")
                .param("dateOfBirth", "2212-231-21")
        )
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("actorDTO"))
                .andExpect(view().name("admin/actor/createActorForm"));
    }


    @Test
    @DisplayName("GET, method = updateActor")
    void updateActor() throws Exception {
        //given
        ActorDTO actorDTO = new ActorDTO();

        //when
        when(actorService.getActorByID(anyLong())).thenReturn(actorDTO);

        //then
        mockMvc.perform(get("/admin/actor/update/1"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("actorDTO"))
                .andExpect(view().name("admin/actor/updateActorForm"));
    }


    @Test
    @DisplayName("GET, method = checkActorUpdate")
    void checkActorUpdateHappyPath() throws Exception {
        //given
        ActorDTO actorDTO = new ActorDTO();

        //when
        when(actorService.createActor(any(ActorDTO.class))).thenReturn(actorDTO);

        mockMvc.perform(post("/admin/actor/update/check")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("firstName", "Adrian")
                .param("lastName", "Romanski")
                .param("country", "Poland")
                .param("dateOfBirth", String.valueOf(LocalDate.of(1944, 3, 4)))
        )
                .andExpect(status().is3xxRedirection())
                .andExpect(model().attributeExists("actorDTO"))
                .andExpect(view().name("redirect:/admin/actor/showActors/page/1"));
    }


    @Test
    @DisplayName("GET, method = showMovies")
    void showMovies() throws Exception {
        // Given
        ActorDTO actorDTO = new ActorDTO();

        List<Movie> movieList = Arrays.asList(new Movie(), new Movie(), new Movie());
        PageRequest pageable = PageRequest.of(0, 10);
        Page<Movie> moviePage = new PageImpl<>(movieList, pageable, movieList.size());

        List<MovieDTO> movieListDTO = Arrays.asList(new MovieDTO(), new MovieDTO(), new MovieDTO());
        PageRequest pageableDTO = PageRequest.of(0, 10);
        Page<MovieDTO> moviePageDTO = new PageImpl<>(movieListDTO, pageableDTO, movieListDTO.size());

        //When
        when(movieService.getAllMoviesPaged(pageable)).thenReturn(moviePage);
        when(movieService.getPageMovieDTO(moviePage, pageable)).thenReturn(moviePageDTO);

        when(actorService.getActorByID(anyLong())).thenReturn(actorDTO);

        //then
        mockMvc.perform(get("/admin/actor/1/addMovies/page/1"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("moviesDTOList"))
                .andExpect(view().name("admin/actor/addMoviesForm"));
    }


    @Test
    @DisplayName("GET, method = addMovieToActor")
    void addMovieToActor() throws Exception {
        ActorDTO actorDTO = new ActorDTO();
        MovieDTO movieDTO = new MovieDTO();

        when(actorService.getActorByID(anyLong())).thenReturn(actorDTO);
        when(movieService.getMovieByID(anyLong())).thenReturn(movieDTO);

        mockMvc.perform(get("/admin/actor/1/movie/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(model().attributeExists("actorDTO"))
                .andExpect(model().attributeExists("movies"))
                .andExpect(view().name("redirect:/admin/actor/showActor/1"));
    }


    @Test
    @DisplayName("Unhappy Path, GET, method = checkActorUpdate")
    void checkActorUpdateUnHappy() throws Exception {
        //given
        ActorDTO actorDTO = new ActorDTO();

        //when
        when(actorService.createActor(any(ActorDTO.class))).thenReturn(actorDTO);

        mockMvc.perform(post("/admin/actor/update/check")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("firstName", "a")
                .param("lastName", "r")
                .param("country", "123")
                .param("dateOfBirth", "2212-231-21")
        )
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("actorDTO"))
                .andExpect(view().name("admin/actor/updateActorForm"));
    }


    @Test
    @DisplayName("GET, method = deleteActor")
    void deleteActor() throws Exception {
        //given
        ActorDTO actorDTO = new ActorDTO();

        //when
        when(actorService.getActorByID(anyLong())).thenReturn(actorDTO);

        //then
        mockMvc.perform(get("/admin/actor/delete/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/admin/actor/showActors/page/1"));
    }


    @Test
    @DisplayName("GET, method = deleteMovie")
    void deleteMovie() throws Exception {
        //given
        ActorDTO actorDTO = new ActorDTO();

        //when
        when(actorService.getActorByID(anyLong())).thenReturn(actorDTO);

        //then
        mockMvc.perform(get("/admin/actor/1/deleteMovie/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/admin/actor/showActor/1"));
    }
}
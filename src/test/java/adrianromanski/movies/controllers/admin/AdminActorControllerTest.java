package adrianromanski.movies.controllers.admin;

import adrianromanski.movies.domain.person.Actor;
import adrianromanski.movies.services.actor.ActorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class AdminActorControllerTest {

    @Mock
    ActorService actorService;

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
}
package adrianromanski.movies.controllers.user;

import adrianromanski.movies.domain.base_entity.Event;
import adrianromanski.movies.services.event.EventServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class EventControllerTest {

    @InjectMocks
    EventController eventController;

    @Mock
    EventServiceImpl eventService;

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);

        mockMvc = MockMvcBuilders.standaloneSetup(eventController).build();
    }


    @Test
    @DisplayName("GET, Happy Path, method = getEventByID")
    void getEventById() throws Exception {
        //given
        Event event = Event.builder().build();
        //when

        when(eventService.getEventByID(anyLong())).thenReturn(event);
        //then

        mockMvc.perform(get("/event/1"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("event"))
                .andExpect(view().name("/user/events/showEvent"));
    }
}
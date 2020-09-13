package adrianromanski.movies.controllers.user;

import adrianromanski.movies.model.base_entity.NewsDTO;
import adrianromanski.movies.services.news.NewsServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

class HomeControllerTest {

    @Mock
    NewsServiceImpl eventService;

    @InjectMocks
    HomeController controller;

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);

        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }


    @Test
    @DisplayName("GET, HappyPath, method = getHome")
    void getHomePage() throws Exception {
        List<NewsDTO> eventList = Arrays.asList(new NewsDTO(), new NewsDTO(), new NewsDTO());

        when(eventService.getLatestNews()).thenReturn(eventList);

        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("user/home"));
    }
}
package adrianromanski.movies.controllers;

import adrianromanski.movies.controllers.user.HomeController;
import adrianromanski.movies.model.base_entity.NewsDTO;
import adrianromanski.movies.services.category.CategoryService;
import adrianromanski.movies.services.news.NewsServiceImpl;
import org.junit.jupiter.api.BeforeEach;
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

    @InjectMocks
    HomeController homeController;

    @Mock
    CategoryService categoryService;

    @Mock
    NewsServiceImpl eventService;

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);

        mockMvc = MockMvcBuilders.standaloneSetup(homeController).build();
    }

    @Test
    void home() throws Exception {
        List<NewsDTO> newsDTOList = Arrays.asList(new NewsDTO(), new NewsDTO(), new NewsDTO());

        when(eventService.getLatestNews()).thenReturn(newsDTOList);

        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("user/home"));
    }
}
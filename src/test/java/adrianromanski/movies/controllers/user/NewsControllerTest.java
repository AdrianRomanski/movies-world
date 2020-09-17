package adrianromanski.movies.controllers.user;

import adrianromanski.movies.domain.base_entity.News;
import adrianromanski.movies.services.news.NewsServiceImpl;
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

class NewsControllerTest {

    @InjectMocks
    NewsController eventController;

    @Mock
    NewsServiceImpl newsService;

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
        News news = News.builder().build();
        //when

        when(newsService.getNewsByID(anyLong())).thenReturn(news);
        //then

        mockMvc.perform(get("/news/1"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("singleNews"))
                .andExpect(view().name("user/news/showSingleNews"));
    }



    @Test
    @DisplayName("GET, Happy Path, method = showNewsSortedDesc")
    void showNewsSortedDesc() throws Exception {
        // Given
        List<News> newsList = Arrays.asList(new News(), new News(), new News());

        PageRequest pageable = PageRequest.of(0, 5, Sort.by("date").descending());
        Page<News> newsPage = new PageImpl<>(newsList, pageable, newsList.size());


        when(newsService.getNewsPaged(pageable)).thenReturn(newsPage);

        mockMvc.perform(get("/news/sorted/desc/date/page/1"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("activeNewsPage"))
                .andExpect(model().attributeExists("newsList"))
                .andExpect(model().attributeExists("pageNumbers"))
                .andExpect(view().name("user/news/showAllNews"));
    }

    @Test
    @DisplayName("GET, Happy Path, method = showNewsSortedAsc")
    void showNewsSortedAsc() throws Exception {
        // Given
        List<News> newsList = Arrays.asList(new News(), new News(), new News());

        PageRequest pageable = PageRequest.of(0, 5, Sort.by("date").ascending());
        Page<News> newsPage = new PageImpl<>(newsList, pageable, newsList.size());


        when(newsService.getNewsPaged(pageable)).thenReturn(newsPage);

        mockMvc.perform(get("/news/sorted/asc/date/page/1"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("activeNewsPage"))
                .andExpect(model().attributeExists("newsList"))
                .andExpect(model().attributeExists("pageNumbers"))
                .andExpect(view().name("user/news/showAllNews"));
    }
}
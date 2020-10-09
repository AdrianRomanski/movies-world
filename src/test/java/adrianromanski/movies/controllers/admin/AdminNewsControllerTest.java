package adrianromanski.movies.controllers.admin;

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
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class AdminNewsControllerTest {

    @InjectMocks
    AdminNewsController controller;

    @Mock
    NewsServiceImpl newsService;

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);

        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    @DisplayName("GET, method = getNewsPaged")
    void getNewsPaged() throws Exception {
        // Given
        List<News> movieList = Arrays.asList(new News(), new News(), new News());
        PageRequest pageable = PageRequest.of(0, 12);
        Page<News> newsPage = new PageImpl<>(movieList, pageable, movieList.size());

        //When
        when(newsService.getNewsPaged(pageable)).thenReturn(newsPage);

        //Then
        mockMvc.perform(get("/admin/news/showNews/page/1"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("newsList"))
                .andExpect(view().name("admin/news/showNewsForm"));
    }


    @Test
    @DisplayName("GET, method = getSingleNews")
    void getSingleNews() throws Exception {
        //given
        News news = new News();
        //when

        when(newsService.getNewsByID(anyLong())).thenReturn(news);

        //Then
        mockMvc.perform(get("/admin/news/1"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("news"))
                .andExpect(view().name("admin/news/showSingleNews"));
    }
}
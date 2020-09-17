package adrianromanski.movies.services.event;

import adrianromanski.movies.domain.base_entity.News;
import adrianromanski.movies.mapper.base_entity.EventMapperImpl;
import adrianromanski.movies.model.base_entity.NewsDTO;
import adrianromanski.movies.repositories.base_entity.EventRepository;
import adrianromanski.movies.repositories.pages.EventPageRepository;
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

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class NewsServiceImplTest {


    public static final LocalDate DATE = LocalDate.now();
    public static final String NAME = "5 Event";
    public static final String DESCRIPTION = "5 Event Description 2 Event Description";

    @Mock
    EventRepository eventRepository;

    @Mock
    EventPageRepository eventPageRepository;

    @Mock
    EventMapperImpl eventMapper;

    @InjectMocks
    NewsServiceImpl newsService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    @DisplayName("Happy Path, method = getLatestEvents")
    void getLatestEvents() {
        //given
        List<News> news = Arrays.asList(new News(), new News(), new News(), new News(), new News());
        //when

        when(eventRepository.findAll()).thenReturn(news);

        //then
        List<NewsDTO> returnDTO = newsService.getLatestNews();

        assertEquals(returnDTO.size(), 5);
    }


    @DisplayName("Happy Path, method = getNewsPaged")
    @Test
    void getAllNewsPaged() {
        List<News> news = Arrays.asList(new News(), new News(), new News(), new News(), new News());

        PageRequest pageable = PageRequest.of(0, 5);

        Page<News> moviePage = new PageImpl<>(news, pageable, news.size());

        when(eventPageRepository.findAll(pageable)).thenReturn(moviePage);

        Page<News> returnNews = newsService.getNewsPaged(pageable);

        assertEquals(returnNews.getTotalElements(), 5);
        assertEquals(returnNews.getTotalPages(), 1);
    }

    @Test
    @DisplayName("Happy Path, method = getEventByID")
    void getEventByID() {
        //given
        News news = News.builder().name(NAME).description(DESCRIPTION).date(LocalDate.now()).build();
        //when

        when(eventRepository.findById(1L)).thenReturn(Optional.of(news));
        //then

        News returnObj = newsService.getNewsByID(1L);

        assertEquals(returnObj.getName(), NAME);
        assertEquals(returnObj.getDescription(), DESCRIPTION);


    }
}
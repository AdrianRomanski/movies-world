package adrianromanski.movies.mapper.base_entity;

import adrianromanski.movies.domain.base_entity.News;
import adrianromanski.movies.model.base_entity.NewsDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class NewsMapperTest {

    public static final String NAME = "New Batman";
    public static final String DESCRIPTION = "The new batman is comming!!!!!!!!!!!";
    public static final long ID = 1L;
    EventMapper mapper = new EventMapperImpl();


    @Test
    @DisplayName("Happy Path, method = eventToEventDTO")
    void eventToEventDTO() {
        //given
        News news = News.builder().name(NAME).description(DESCRIPTION).id(ID).build();
        //when

        NewsDTO newsDTO = mapper.eventToEventDTO(news);

        //then
        assertEquals(newsDTO.getName(), NAME);
        assertEquals(newsDTO.getDescription(), DESCRIPTION);
        assertEquals(newsDTO.getId(), ID);
    }


    @Test
    @DisplayName("Happy Path, method = eventDTOToEvent")
    void eventDTOToEvent() {
        //given
        NewsDTO newsDTO = NewsDTO.builder().name(NAME).description(DESCRIPTION).id(ID).build();
        //when

        News news = mapper.eventDTOToEvent(newsDTO);

        //then
        assertEquals(news.getName(), NAME);
        assertEquals(news.getDescription(), DESCRIPTION);
        assertEquals(news.getId(), ID);
    }
}
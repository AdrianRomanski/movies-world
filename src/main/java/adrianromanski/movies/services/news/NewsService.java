package adrianromanski.movies.services.news;

import adrianromanski.movies.domain.base_entity.News;
import adrianromanski.movies.model.base_entity.NewsDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;


public interface NewsService {
//
//    EventDTO addEvent(Event event);

    News getNewsByID(Long id);

    List<NewsDTO> getLatestNews();

    Page<News> getNewsPaged(Pageable pageable);

    void deleteNews(Long id);
}

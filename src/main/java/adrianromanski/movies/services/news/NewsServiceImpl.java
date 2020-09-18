package adrianromanski.movies.services.news;

import adrianromanski.movies.aspects.paging_log.LogPaging;
import adrianromanski.movies.domain.base_entity.News;
import adrianromanski.movies.exceptions.ResourceNotFoundException;
import adrianromanski.movies.mapper.base_entity.EventMapper;
import adrianromanski.movies.model.base_entity.NewsDTO;
import adrianromanski.movies.repositories.base_entity.EventRepository;
import adrianromanski.movies.repositories.pages.EventPageRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static java.util.stream.Collectors.toList;


@Service
@AllArgsConstructor
public class NewsServiceImpl implements NewsService {

    EventRepository eventRepository;
    EventPageRepository eventPageRepository;
    EventMapper eventMapper;


//    @Override
//    public EventDTO addEvent(Event event) {
//        eventRepository.save(event);
//        return eventMapper.eventToEventDTO(event);
//    }


    /**
     * Returns News with matching id
     */
    @Override
    public News getNewsByID(Long id) {
        return eventRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(id, News.class));
    }


    /**
     * Returns 3 Latest News
     */
    @Override
    public List<NewsDTO> getLatestNews() {
        List<News> evens = new ArrayList<>();
        new LinkedList<>(eventRepository.findAll())
                .descendingIterator()
                .forEachRemaining(evens::add);
        return evens.stream()
                .limit(5)
                .map(eventMapper::eventToEventDTO)
                .collect(toList());
    }


    @Override
    @LogPaging
    public Page<News> getNewsPaged(Pageable pageable) {
        return eventPageRepository.findAll(pageable);
    }
}




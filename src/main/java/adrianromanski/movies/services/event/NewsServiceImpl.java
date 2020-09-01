package adrianromanski.movies.services.event;

import adrianromanski.movies.domain.base_entity.Event;
import adrianromanski.movies.exceptions.ResourceNotFoundException;
import adrianromanski.movies.mapper.base_entity.EventMapper;
import adrianromanski.movies.model.base_entity.EventDTO;
import adrianromanski.movies.repositories.base_entity.EventRepository;
import adrianromanski.movies.repositories.pages.EventPageRepository;
import lombok.AllArgsConstructor;
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
     * Returns Event with matching id
     */
    @Override
    public Event getEventByID(Long id) {
        return eventRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(id, Event.class));
    }


    /**
     * Returns 3 Latest Events
     */
    @Override
    public List<EventDTO> getLatestEvents() {
        List<Event> evens = new ArrayList<>();
        new LinkedList<>(eventRepository.findAll())
                .descendingIterator()
                .forEachRemaining(evens::add);
        return evens.stream()
                .limit(3)
                .map(eventMapper::eventToEventDTO)
                .collect(toList());
    }
}



//    @Override
//    public Page<Event> getEventsPaged(Pageable pageable) {
//        return eventPageRepository.findAll(pageable);
//    }




package adrianromanski.movies.services.event;

import adrianromanski.movies.domain.base_entity.Event;
import adrianromanski.movies.model.base_entity.EventDTO;

import java.util.List;


public interface EventService {
//
//    EventDTO addEvent(Event event);

    Event getEventByID(Long id);

    List<EventDTO> getLatestEvents();

//
//    Page<Event> getEventsPaged(Pageable pageable);
}

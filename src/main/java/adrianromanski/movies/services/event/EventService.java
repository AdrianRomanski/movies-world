package adrianromanski.movies.services.event;

import adrianromanski.movies.model.base_entity.EventDTO;

import java.util.List;


public interface EventService {
//
//    EventDTO addEvent(Event event);

    List<EventDTO> getLatestEvents();

//
//    Page<Event> getEventsPaged(Pageable pageable);
}

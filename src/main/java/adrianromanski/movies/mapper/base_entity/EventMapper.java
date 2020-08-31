package adrianromanski.movies.mapper.base_entity;

import adrianromanski.movies.domain.base_entity.Event;
import adrianromanski.movies.model.base_entity.EventDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel="spring")
public interface EventMapper {

    EventDTO eventToEventDTO(Event event);

    Event eventDTOToEvent(EventDTO eventDTO);
}

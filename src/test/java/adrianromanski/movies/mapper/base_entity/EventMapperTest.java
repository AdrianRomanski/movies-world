package adrianromanski.movies.mapper.base_entity;

import adrianromanski.movies.domain.base_entity.Event;
import adrianromanski.movies.model.base_entity.EventDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class EventMapperTest {

    public static final String NAME = "New Batman";
    public static final String DESCRIPTION = "The new batman is comming!!!!!!!!!!!";
    public static final long ID = 1L;
    EventMapper mapper = new EventMapperImpl();


    @Test
    @DisplayName("Happy Path, method = eventToEventDTO")
    void eventToEventDTO() {
        //given
        Event event = Event.builder().name(NAME).description(DESCRIPTION).id(ID).build();
        //when

        EventDTO eventDTO = mapper.eventToEventDTO(event);

        //then
        assertEquals(eventDTO.getName(), NAME);
        assertEquals(eventDTO.getDescription(), DESCRIPTION);
        assertEquals(eventDTO.getId(), ID);
    }


    @Test
    @DisplayName("Happy Path, method = eventDTOToEvent")
    void eventDTOToEvent() {
        //given
        EventDTO eventDTO = EventDTO.builder().name(NAME).description(DESCRIPTION).id(ID).build();
        //when

        Event event = mapper.eventDTOToEvent(eventDTO);

        //then
        assertEquals(event.getName(), NAME);
        assertEquals(event.getDescription(), DESCRIPTION);
        assertEquals(event.getId(), ID);
    }
}
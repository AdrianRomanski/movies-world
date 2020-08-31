package adrianromanski.movies.services.event;

import adrianromanski.movies.domain.base_entity.Event;
import adrianromanski.movies.mapper.base_entity.EventMapperImpl;
import adrianromanski.movies.model.base_entity.EventDTO;
import adrianromanski.movies.repositories.base_entity.EventRepository;
import adrianromanski.movies.repositories.pages.EventPageRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class EventServiceImplTest {

    @Mock
    EventRepository eventRepository;

    @Mock
    EventPageRepository eventPageRepository;

    @Mock
    EventMapperImpl eventMapper;

    @InjectMocks
    EventServiceImpl eventService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    @DisplayName("Happy Path, method = getLatestEvents")
    void getLatestEvents() {
        //given
        List<Event> events = Arrays.asList(new Event(), new Event(), new Event(), new Event(), new Event());
        //when

        when(eventRepository.findAll()).thenReturn(events);

        //then
        List<EventDTO> returnDTO = eventService.getLatestEvents();

        assertEquals(returnDTO.size(), 3);
    }
}
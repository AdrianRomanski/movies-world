package adrianromanski.movies.services.series;

import adrianromanski.movies.domain.base_entity.Episode;
import adrianromanski.movies.domain.base_entity.Series;
import adrianromanski.movies.exceptions.ResourceNotFoundException;
import adrianromanski.movies.jms.JmsTextMessageService;
import adrianromanski.movies.mapper.base_entity.SeriesMapper;
import adrianromanski.movies.mapper.base_entity.SeriesMapperImpl;
import adrianromanski.movies.model.base_entity.SeriesDTO;
import adrianromanski.movies.repositories.base_entity.SeriesRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class SeriesServiceImplTest {

    public static final String NAME = "Breaking Bad";
    public static final String DESCRIPTION = "Chemistry Teacher finding new hobby";
    public static final String URL = "someurl";
    public static final long ID = 1L;
    public static final String NAME1 = "First";
    public static final String NAME2 = "Second";


    @Mock
    SeriesRepository seriesRepository;

    @Mock
    JmsTextMessageService jms;

    SeriesService seriesService;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);

        SeriesMapper seriesMapper = new SeriesMapperImpl();

        seriesService = new SeriesServiceImpl(seriesRepository, seriesMapper, jms);
    }

    private Series getSeries() {
        Episode episode1 = new Episode();
        episode1.setName(NAME1);
        Episode episode2 = new Episode();
        episode2.setName(NAME2);

        return Series.builder().name(NAME).description(DESCRIPTION).imageURL(URL).id(ID).episodes(Arrays.asList(episode1,episode2)).build();
    }

    private SeriesDTO getSeriesDTO() {
        return SeriesDTO.builder().name(NAME).description(DESCRIPTION).imageURL(URL).id(ID).build();
    }


    @DisplayName("Happy Path, method = getSeriesByID")
    @Test
    void getSeriesByIDHappyPath() {
        Series series = getSeries();

        when(seriesRepository.findById(anyLong())).thenReturn(Optional.of(series));

        SeriesDTO returnDTO = seriesService.getSeriesByID(1L);

        assertEquals(returnDTO.getDescription(), DESCRIPTION);
        assertEquals(returnDTO.getId(), ID);
        assertEquals(returnDTO.getImageURL(), URL);
        assertEquals(returnDTO.getName(), NAME);

        assertEquals(returnDTO.getEpisodesDTO().size(), 2);
    }


    @DisplayName("UnHappy Path, method = getSeriesByID")
    @Test
    void getSeriesByIDUnHappyHappyPath() {
        Throwable ex = catchThrowable(() -> seriesService.getSeriesByID(1L));

        assertThat(ex).isInstanceOf(ResourceNotFoundException.class);
    }


    @DisplayName("Happy Path, method = createSeries")
    @Test
    void createSeries() {
        SeriesDTO seriesDTO = getSeriesDTO();

        SeriesDTO returnDTO = seriesService.createSeries(seriesDTO);

        assertEquals(returnDTO.getDescription(), DESCRIPTION);
        assertEquals(returnDTO.getId(), ID);
        assertEquals(returnDTO.getImageURL(), URL);
        assertEquals(returnDTO.getName(), NAME);

        verify(seriesRepository, times(1)).save(any(Series.class));
    }


    @DisplayName("Happy Path, method = updateSeries")
    @Test
    void updateSeriesHappyPath() {
        Series series = getSeries();
        SeriesDTO seriesDTO = getSeriesDTO();

        when(seriesRepository.findById(anyLong())).thenReturn(Optional.of(series));

        SeriesDTO returnDTO = seriesService.updateSeries(1L, seriesDTO);

        assertEquals(returnDTO.getDescription(), DESCRIPTION);
        assertEquals(returnDTO.getId(), ID);
        assertEquals(returnDTO.getImageURL(), URL);
        assertEquals(returnDTO.getName(), NAME);

        verify(seriesRepository, times(1)).save(any(Series.class));
    }


    @DisplayName("UnHappy Path, method = updateSeries")
    @Test
    void updateSeriesUnHappyPath() {
        Throwable ex = catchThrowable(() -> seriesService.updateSeries(1L, new SeriesDTO()));

        assertThat(ex).isInstanceOf(ResourceNotFoundException.class);
    }


    @DisplayName("Happy Path, method = deleteSeries")
    @Test
    void deleteSeriesHappyPath() {
        Series series = getSeries();

        when(seriesRepository.findById(anyLong())).thenReturn(Optional.of(series));

        seriesService.deleteSeries(1L);

        verify(seriesRepository, times(1)).deleteById(anyLong());
    }


    @DisplayName("UnHappy Path, method = deleteSeries")
    @Test
    void deleteSeriesUnHappyPath() {
        Throwable ex = catchThrowable(() -> seriesService.deleteSeries(1L));

        assertThat(ex).isInstanceOf(ResourceNotFoundException.class);
    }
}
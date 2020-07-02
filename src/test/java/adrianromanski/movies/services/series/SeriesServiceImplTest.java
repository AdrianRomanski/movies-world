package adrianromanski.movies.services.series;

import adrianromanski.movies.domain.base_entity.Episode;
import adrianromanski.movies.domain.base_entity.Series;
import adrianromanski.movies.exceptions.ResourceNotFoundException;
import adrianromanski.movies.jms.JmsTextMessageService;
import adrianromanski.movies.mapper.base_entity.EpisodeMapper;
import adrianromanski.movies.mapper.base_entity.EpisodeMapperImpl;
import adrianromanski.movies.mapper.base_entity.SeriesMapper;
import adrianromanski.movies.mapper.base_entity.SeriesMapperImpl;
import adrianromanski.movies.model.base_entity.EpisodeDTO;
import adrianromanski.movies.model.base_entity.SeriesDTO;
import adrianromanski.movies.repositories.base_entity.EpisodeRepository;
import adrianromanski.movies.repositories.base_entity.SeriesRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
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
    public static final String EPISODE_DESC = "Final battle";
    public static final String EPISODE_NAME = "Battle of Titans";


    @Mock
    SeriesRepository seriesRepository;

    @Mock
    EpisodeRepository episodeRepository;

    @Mock
    JmsTextMessageService jms;

    SeriesService seriesService;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);

        SeriesMapper seriesMapper = new SeriesMapperImpl();
        EpisodeMapper episodeMapper = new EpisodeMapperImpl();

        seriesService = new SeriesServiceImpl(seriesRepository, episodeRepository,
                                                seriesMapper, episodeMapper, jms);
    }

    private Series getSeries() {
        Episode episode1 = new Episode();
        episode1.setName(NAME1);
        episode1.setId(ID);
        Episode episode2 = new Episode();
        episode2.setName(NAME2);
        episode2.setId(ID);
        List<Episode> episodeList = new ArrayList<>();
        episodeList.add(episode1);
        episodeList.add(episode2);

        return Series.builder().name(NAME).description(DESCRIPTION).imageURL(URL).id(ID).episodes(episodeList).build();
    }

    private SeriesDTO getSeriesDTO() { return SeriesDTO.builder().name(NAME).description(DESCRIPTION).imageURL(URL).id(ID).build(); }
    private EpisodeDTO getEpisodeDTO() { return EpisodeDTO.builder().description(EPISODE_DESC).name(EPISODE_NAME).id(ID).build(); }
    private Episode getEpisode() { return Episode.builder().description(EPISODE_DESC).name(EPISODE_NAME).id(ID).build(); }


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
    void getSeriesByIDUnHappy() {
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


    @DisplayName("Happy Path, method = addEpisode")
    @Test
    void addEpisodeHappyPath() {
        Series series = getSeries();

        EpisodeDTO episodeDTO = getEpisodeDTO();

        when(seriesRepository.findById(anyLong())).thenReturn(Optional.of(series));

        EpisodeDTO returnDTO = seriesService.addEpisode(1L, episodeDTO);

        assertEquals(returnDTO.getDescription(), EPISODE_DESC);
        assertEquals(returnDTO.getName(), EPISODE_NAME);

        verify(seriesRepository, times(1)).save(any(Series.class));
        verify(episodeRepository, times(1)).save(any(Episode.class));
    }


    @DisplayName("UnHappy Path, method = addEpisode")
    @Test
    void addEpisodeUnHappyPath() {
        Throwable ex = catchThrowable(() -> seriesService.addEpisode(1L, new EpisodeDTO()));

        assertThat(ex).isInstanceOf(ResourceNotFoundException.class);
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


    @DisplayName("Happy Path, method = updateEpisode")
    @Test
    void updateEpisodeHappyPath() {
        Series series = getSeries();
        Episode episode = getEpisode();
        series.getEpisodes().add(episode);
        episode.setSeries(series);
        EpisodeDTO episodeDTO = getEpisodeDTO();

        when(seriesRepository.findById(anyLong())).thenReturn(Optional.of(series));

        EpisodeDTO returnDTO = seriesService.updateEpisode(1L, 1L, episodeDTO);

        assertEquals(returnDTO.getDescription(), EPISODE_DESC);
        assertEquals(returnDTO.getName(), EPISODE_NAME);

        verify(seriesRepository, times(1)).save(any(Series.class));
        verify(episodeRepository, times(1)).save(any(Episode.class));;
    }


    @DisplayName("UnHappy Path, method = updateEpisode, reason = Series not found")
    @Test
    void updateEpisodeUnHappyPathSeriesNotFound() {
        Throwable ex = catchThrowable(() -> seriesService.updateEpisode(1L, 1L, new EpisodeDTO()));

        assertThat(ex).isInstanceOf(ResourceNotFoundException.class);
    }


    @DisplayName("UnHappy Path, method = updateEpisode, reason = Episode not found")
    @Test
    void updateEpisodeUnHappyPathEpisodeNotFound() {
        Series series = new Series();
        when(seriesRepository.findById(anyLong())).thenReturn(Optional.of(series));

        Throwable ex = catchThrowable(() -> seriesService.updateEpisode(1L,1L, new EpisodeDTO()));

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


    @DisplayName("Happy Path, method = deleteEpisode")
    @Test
    void deleteEpisodeHappyPath() {
        Series series = new Series();
        series.setId(ID);
        Episode episode = new Episode();
        episode.setId(ID);
        series.getEpisodes().add(episode);
        episode.setSeries(series);

        when(seriesRepository.findById(anyLong())).thenReturn(Optional.of(series));

        seriesService.deleteEpisode(1L, 1L);

        verify(episodeRepository, times(1)).deleteById(anyLong());
        verify(seriesRepository, times(1)).save(any(Series.class));
    }


    @DisplayName("UnHappy Path, method = deleteEpisode, reason = Series not found")
    @Test
    void deleteEpisodeUnHappyPathSeriesNotFound() {
        Throwable ex = catchThrowable(() -> seriesService.deleteEpisode(1L,1L));

        assertThat(ex).isInstanceOf(ResourceNotFoundException.class);
    }


    @DisplayName("UnHappy Path, method = deleteEpisode, reason = Episode not found")
    @Test
    void deleteEpisodeUnHappyPathEpisodeNotFound() {
        Series series = new Series();

        when(seriesRepository.findById(anyLong())).thenReturn(Optional.of(series));

        Throwable ex = catchThrowable(() -> seriesService.deleteEpisode(1L, 1L));

        assertThat(ex).isInstanceOf(ResourceNotFoundException.class);
    }

}
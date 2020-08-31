package adrianromanski.movies.mapper.base_entity;

import adrianromanski.movies.domain.base_entity.Episode;
import adrianromanski.movies.domain.base_entity.Series;
import adrianromanski.movies.model.base_entity.EpisodeDTO;
import adrianromanski.movies.model.base_entity.SeriesDTO;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SeriesMapperTest {

    public static final String NAME = "Breaking Bad";
    public static final String DESCRIPTION = "Chemistry Teacher finding new hobby";
    public static final String URL = "someurl";
    public static final long ID = 1L;
    public static final String NAME1 = "First";
    public static final String NAME2 = "Second";
    SeriesMapper mapper = new SeriesMapperImpl();


    @Test
    void seriesToSeriesDTO() {
        Episode episode1 = Episode.builder().name(NAME1).build();
        Episode episode2 =  Episode.builder().name(NAME2).build();

        Series series = Series.builder().name(NAME).description(DESCRIPTION).episodes(Arrays.asList(episode1, episode2)).id(ID).build();

        episode1.setSeries(series);
        episode2.setSeries(series);

        SeriesDTO seriesDTO = mapper.seriesToSeriesDTO(series);

        assertEquals(seriesDTO.getName(), NAME);
        assertEquals(seriesDTO.getDescription(), DESCRIPTION);
        assertEquals(seriesDTO.getId(), ID);

        assertEquals(seriesDTO.getEpisodesDTO().get(0).getName(), NAME1);
        assertEquals(seriesDTO.getEpisodesDTO().get(1).getName(), NAME2);
    }

    @Test
    void seriesDTOToSeries() {
        EpisodeDTO episode1DTO = EpisodeDTO.builder().name(NAME1).build();
        EpisodeDTO episode2DTO =  EpisodeDTO.builder().name(NAME2).build();

        List<EpisodeDTO> episodeDTOList = new ArrayList<>();

        episodeDTOList.add(episode1DTO);
        episodeDTOList.add(episode2DTO);

        SeriesDTO seriesDTO = SeriesDTO.builder().name(NAME).description(DESCRIPTION).id(ID).episodesDTO(episodeDTOList).build();

        Series series = mapper.seriesDTOToSeries(seriesDTO);

        assertEquals(series.getName(), NAME);
        assertEquals(series.getDescription(), DESCRIPTION);
        assertEquals(series.getId(), ID);

        assertEquals(series.getEpisodes().get(0).getName(), NAME1);
        assertEquals(series.getEpisodes().get(1).getName(), NAME2);
    }
}
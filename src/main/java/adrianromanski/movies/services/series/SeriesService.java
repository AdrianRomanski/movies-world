package adrianromanski.movies.services.series;

import adrianromanski.movies.model.base_entity.EpisodeDTO;
import adrianromanski.movies.model.base_entity.SeriesDTO;

public interface SeriesService {

    //GET
    SeriesDTO getSeriesByID(Long id);

    //POST
    SeriesDTO createSeries(SeriesDTO seriesDTO);

    EpisodeDTO addEpisode(Long id, EpisodeDTO episodeDTO);

    //PUT
    SeriesDTO updateSeries(Long id, SeriesDTO seriesDTO);

    EpisodeDTO updateEpisode(Long seriesID, Long episodeID, EpisodeDTO episodeDTO);

    //DELETE
    void deleteSeries(Long id);

    void deleteEpisode(Long seriesID, Long episodeID);
}

package adrianromanski.movies.services.series;

import adrianromanski.movies.aspects.creation_log.LogCreation;
import adrianromanski.movies.aspects.delete_log.LogDelete;
import adrianromanski.movies.aspects.update_log.LogUpdate;
import adrianromanski.movies.domain.base_entity.Episode;
import adrianromanski.movies.domain.base_entity.Series;
import adrianromanski.movies.exceptions.ResourceNotFoundException;
import adrianromanski.movies.mapper.base_entity.EpisodeMapper;
import adrianromanski.movies.mapper.base_entity.SeriesMapper;
import adrianromanski.movies.model.base_entity.EpisodeDTO;
import adrianromanski.movies.model.base_entity.SeriesDTO;
import adrianromanski.movies.repositories.base_entity.EpisodeRepository;
import adrianromanski.movies.repositories.base_entity.SeriesRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class SeriesServiceImpl implements SeriesService {

    private final SeriesRepository seriesRepository;
    private final EpisodeRepository episodeRepository;
    private final SeriesMapper seriesMapper;
    private final EpisodeMapper episodeMapper;

    /**
     * @param id of the Series we are looking for
     * @return SeriesDTO
     * @throws ResourceNotFoundException if not found
     */
    @Override
    public SeriesDTO getSeriesByID(Long id) {
        return seriesRepository.findById(id)
                .map(seriesMapper::seriesToSeriesDTO)
                .orElseThrow(() -> new ResourceNotFoundException(id, Series.class));
    }


    /**
     * @param seriesDTO body
     * @return SeriesDTO if successfully saved to database
     */
    @Override
    @LogCreation
    public SeriesDTO createSeries(SeriesDTO seriesDTO) {
        Series series = seriesMapper.seriesDTOToSeries(seriesDTO);
        seriesRepository.save(series);
        return seriesMapper.seriesToSeriesDTO(series);
    }


    /**
     * @param id of the Series
     * @param episodeDTO body
     * @return EpisodeDTO if successfully added
     */
    @Override
    public EpisodeDTO addEpisode(Long id, EpisodeDTO episodeDTO) {
        Series series = seriesRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(id, Series.class));
        Episode episode = episodeMapper.episodeDTOToEpisode(episodeDTO);
        series.getEpisodes().add(episode);
        episode.setSeries(series);
        seriesRepository.save(series);
        episodeRepository.save(episode);
        return episodeMapper.episodeToEpisodeDTO(episode);
    }


    /**
     * @param id of the Series we want to update
     * @param seriesDTO body
     * @return SeriesDTO if successfully updated
     * @throws ResourceNotFoundException if not found
     */
    @Override
    @LogUpdate
    public SeriesDTO updateSeries(Long id, SeriesDTO seriesDTO) {
        Series series = seriesRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(id, Series.class));
        Series updatedSeries = seriesMapper.seriesDTOToSeries(seriesDTO);
        series.getEpisodes()
                .forEach(e -> updatedSeries.getEpisodes().add(e));
        seriesRepository.save(updatedSeries);
        return seriesMapper.seriesToSeriesDTO(updatedSeries);
    }


    /**
     * @param seriesID Id of the Series we want to update Episode
     * @param episodeID Id of the Episode
     * @param episodeDTO body
     * @return EpisodeDTO if successfully updated
     * @throws ResourceNotFoundException if Series or Episode not found
     */
    @Override
    @LogUpdate
    public EpisodeDTO updateEpisode(Long seriesID, Long episodeID, EpisodeDTO episodeDTO) {
        Series series = seriesRepository.findById(seriesID)
                .orElseThrow(() -> new ResourceNotFoundException(seriesID, Series.class));
        Episode episode = series.getEpisodeOptional(episodeID)
                .orElseThrow(() -> new ResourceNotFoundException(episodeID, Episode.class));
        series.getEpisodes().remove(episode);
        Episode updated = episodeMapper.episodeDTOToEpisode(episodeDTO);
        episodeDTO.setId(episodeID);
        series.getEpisodes().add(updated);
        seriesRepository.save(series);
        episodeRepository.save(episode);
        return episodeMapper.episodeToEpisodeDTO(updated);
    }


    /**
     * It will delete Series with all the episodes - Cascade.All, orphanRemoval
     * @see javax.persistence.CascadeType
     * @see javax.persistence.OneToMany#orphanRemoval()
     * @param id of the Series we want to delete
     */
    @Override
    @LogDelete
    public void deleteSeries(Long id) {
        seriesRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(id, Series.class));
        seriesRepository.deleteById(id);
    }


    /**
     * @param seriesID ID of the Series we want to delete episode
     * @param episodeID ID of the Episode
     * @throws ResourceNotFoundException Episode or Series not found
     */
    @LogDelete
    @Override
    public void deleteEpisode(Long seriesID, Long episodeID) {
        Series series = seriesRepository.findById(seriesID)
                .orElseThrow(() -> new ResourceNotFoundException(seriesID, Series.class));
        Episode episode = series.getEpisodeOptional(episodeID)
                .orElseThrow(() -> new ResourceNotFoundException(episodeID, Episode.class));
        series.getEpisodes().remove(episode);
        seriesRepository.save(series);
        episodeRepository.deleteById(episodeID);
    }
}

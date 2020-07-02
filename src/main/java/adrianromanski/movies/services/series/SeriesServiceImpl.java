package adrianromanski.movies.services.series;

import adrianromanski.movies.domain.base_entity.Episode;
import adrianromanski.movies.domain.base_entity.Series;
import adrianromanski.movies.exceptions.ResourceNotFoundException;
import adrianromanski.movies.jms.JmsTextMessageService;
import adrianromanski.movies.mapper.base_entity.EpisodeMapper;
import adrianromanski.movies.mapper.base_entity.SeriesMapper;
import adrianromanski.movies.model.base_entity.EpisodeDTO;
import adrianromanski.movies.model.base_entity.SeriesDTO;
import adrianromanski.movies.repositories.base_entity.EpisodeRepository;
import adrianromanski.movies.repositories.base_entity.SeriesRepository;
import org.springframework.stereotype.Service;

@Service
public class SeriesServiceImpl implements SeriesService {

    private final SeriesRepository seriesRepository;
    private final EpisodeRepository episodeRepository;
    private final SeriesMapper seriesMapper;
    private final EpisodeMapper episodeMapper;
    private final JmsTextMessageService jms;


    public SeriesServiceImpl(SeriesRepository seriesRepository, EpisodeRepository episodeRepository,
                             SeriesMapper seriesMapper, EpisodeMapper episodeMapper,
                             JmsTextMessageService jms) {
        this.seriesRepository = seriesRepository;
        this.episodeRepository = episodeRepository;
        this.seriesMapper = seriesMapper;
        this.episodeMapper = episodeMapper;
        this.jms = jms;
    }


    /**
     * @param id of the Series we are looking for
     * @return SeriesDTO
     * @throws ResourceNotFoundException if not found
     */
    @Override
    public SeriesDTO getSeriesByID(Long id) {
        jms.sendTextMessage("Looking for Series with id: " + id);
        return seriesRepository.findById(id)
                .map(seriesMapper::seriesToSeriesDTO)
                .orElseThrow(() -> new ResourceNotFoundException(id, Series.class));
    }


    /**
     * @param seriesDTO body
     * @return SeriesDTO if successfully saved to database
     */
    @Override
    public SeriesDTO createSeries(SeriesDTO seriesDTO) {
        jms.sendTextMessage("Creating new Series with name: " + seriesDTO.getName());
        Series series = seriesMapper.seriesDTOToSeries(seriesDTO);
        seriesRepository.save(series);
        jms.sendTextMessage("Series with name: " + seriesDTO.getName() + " successfully saved to database");
        return seriesMapper.seriesToSeriesDTO(series);
    }


    /**
     * @param id of the Series
     * @param episodeDTO body
     * @return EpisodeDTO if successfully added
     */
    @Override
    public EpisodeDTO addEpisode(Long id, EpisodeDTO episodeDTO) {
        jms.sendTextMessage("Adding episode to Series with id: " + id);
        Series series = seriesRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(id, Series.class));
        Episode episode = episodeMapper.episodeDTOToEpisode(episodeDTO);
        series.getEpisodes().add(episode);
        episode.setSeries(series);
        seriesRepository.save(series);
        episodeRepository.save(episode);
        jms.sendTextMessage("Successfully added episode to Series with id: " + id);
        return episodeMapper.episodeToEpisodeDTO(episode);
    }


    /**
     * @param id of the Series we want to update
     * @param seriesDTO body
     * @return SeriesDTO if successfully updated
     * @throws ResourceNotFoundException if not found
     */
    @Override
    public SeriesDTO updateSeries(Long id, SeriesDTO seriesDTO) {
        jms.sendTextMessage("Updating  Series with id: " + id);
        Series series = seriesRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(id, Series.class));
        Series updatedSeries = seriesMapper.seriesDTOToSeries(seriesDTO);
        series.getEpisodes()
                .forEach(e -> updatedSeries.getEpisodes().add(e));
        seriesRepository.save(updatedSeries);
        jms.sendTextMessage("Series with id: " + id + " successfully updated");
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
    public EpisodeDTO updateEpisode(Long seriesID, Long episodeID, EpisodeDTO episodeDTO) {
        jms.sendTextMessage("Updating  Episode with id: " + episodeID);
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
        jms.sendTextMessage("Episode with id: " + episodeID + " successfully updated");
        return episodeMapper.episodeToEpisodeDTO(updated);
    }


    /**
     * It will delete Series with all the episodes - Cascade.All, orphanRemoval
     * @see javax.persistence.CascadeType
     * @see javax.persistence.OneToMany#orphanRemoval()
     * @param id of the Series we want to delete
     */
    @Override
    public void deleteSeries(Long id) {
        jms.sendTextMessage("Deleting  Series with id: " + id);
        seriesRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(id, Series.class));
        seriesRepository.deleteById(id);
        jms.sendTextMessage("Successfully deleted Series with id: " + id);
    }


    /**
     * @param seriesID ID of the Series we want to delete episode
     * @param episodeID ID of the Episode
     * @throws ResourceNotFoundException Episode or Series not found
     */
    @Override
    public void deleteEpisode(Long seriesID, Long episodeID) {
        jms.sendTextMessage("Deleting  Episode with id: " + episodeID);
        Series series = seriesRepository.findById(seriesID)
                .orElseThrow(() -> new ResourceNotFoundException(seriesID, Series.class));
        Episode episode = series.getEpisodeOptional(episodeID)
                .orElseThrow(() -> new ResourceNotFoundException(episodeID, Episode.class));
        series.getEpisodes().remove(episode);
        seriesRepository.save(series);
        episodeRepository.deleteById(episodeID);
        jms.sendTextMessage("Episode with id: " + episodeID + "successfully deleted");
    }
}

package adrianromanski.movies.services.series;

import adrianromanski.movies.domain.base_entity.Series;
import adrianromanski.movies.exceptions.ResourceNotFoundException;
import adrianromanski.movies.jms.JmsTextMessageService;
import adrianromanski.movies.mapper.base_entity.SeriesMapper;
import adrianromanski.movies.model.base_entity.SeriesDTO;
import adrianromanski.movies.repositories.base_entity.SeriesRepository;
import org.springframework.stereotype.Service;

@Service
public class SeriesServiceImpl implements SeriesService {

    private final SeriesRepository seriesRepository;
    private final SeriesMapper seriesMapper;
    private final JmsTextMessageService jms;

    public SeriesServiceImpl(SeriesRepository seriesRepository, SeriesMapper seriesMapper, JmsTextMessageService jms) {
        this.seriesRepository = seriesRepository;
        this.seriesMapper = seriesMapper;
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
}

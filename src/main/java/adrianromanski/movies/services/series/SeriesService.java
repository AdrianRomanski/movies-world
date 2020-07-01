package adrianromanski.movies.services.series;

import adrianromanski.movies.model.base_entity.SeriesDTO;

public interface SeriesService {

    //GET
    SeriesDTO getSeriesByID(Long id);

    //POST
    SeriesDTO createSeries(SeriesDTO seriesDTO);

    //PUT
    SeriesDTO updateSeries(Long id, SeriesDTO seriesDTO);

    //DELETE
    void deleteSeries(Long id);
}

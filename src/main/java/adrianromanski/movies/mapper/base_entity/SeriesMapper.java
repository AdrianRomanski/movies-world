package adrianromanski.movies.mapper.base_entity;

import adrianromanski.movies.domain.base_entity.Series;
import adrianromanski.movies.model.base_entity.SeriesDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel="spring")
public interface SeriesMapper {


    @Mappings({
        @Mapping(source = "episodes", target = "episodesDTO")
    })
    SeriesDTO seriesToSeriesDTO(Series series);


    @Mappings({
        @Mapping(source = "episodesDTO", target = "episodes")
    })
    Series seriesDTOToSeries(SeriesDTO seriesDTO);
}

package adrianromanski.movies.mapper.base_entity;

import adrianromanski.movies.domain.base_entity.Movie;
import adrianromanski.movies.model.base_entity.MovieDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel="spring")
public interface MovieMapper {

    @Mappings({
            @Mapping(source = "director", target = "directorDTO"),
            @Mapping(source = "actors", target = "actorsDTO"),
            @Mapping(source = "category", target = "categoryDTO"),
            @Mapping(source = "awards", target = "awardsDTO"),
            @Mapping(source = "userWatched", target = "userWatchedDTO"),
            @Mapping(source = "userFavourites", target = "userFavouritesDTO")
    })
    MovieDTO movieToMovieDTO(Movie movie);

    @Mappings({
            @Mapping(source = "directorDTO", target = "director"),
            @Mapping(source = "actorsDTO", target = "actors"),
            @Mapping(source = "categoryDTO", target = "category"),
            @Mapping(source = "awardsDTO", target = "awards"),
            @Mapping(source = "userWatchedDTO", target = "userWatched"),
            @Mapping(source = "userFavouritesDTO", target = "userFavourites")
    })
    Movie movieDTOToMovie(MovieDTO movieDTO);
}

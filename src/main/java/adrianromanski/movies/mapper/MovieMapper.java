package adrianromanski.movies.mapper;

import adrianromanski.movies.domain.Movie;
import adrianromanski.movies.model.MovieDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel="spring")
public interface MovieMapper {

    @Mappings({
            @Mapping(source = "actors", target = "actorsDTO"),
            @Mapping(source = "category", target = "categoryDTO"),
            @Mapping(source = "awards", target = "awardsDTO")
    })
    MovieDTO movieToMovieDTO(Movie movie);

    @Mappings({
            @Mapping(source = "actorsDTO", target = "actors"),
            @Mapping(source = "categoryDTO", target = "category"),
            @Mapping(source = "awardsDTO", target = "awards")
    })
    Movie movieDTOToMovie(MovieDTO movieDTO);
}

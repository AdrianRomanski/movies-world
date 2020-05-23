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
            @Mapping(source = "category", target = "categoryDTO")
    })
    MovieDTO movieToMovieDTO(Movie movie);

    @Mappings({
            @Mapping(source = "actorsDTO", target = "actors"),
            @Mapping(source = "categoryDTO", target = "category")
    })
    Movie movieDTOToMovie(MovieDTO movieDTO);
}

package adrianromanski.movies.mapper;

import adrianromanski.movies.domain.Movie;
import adrianromanski.movies.model.MovieDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel="spring")
public interface MovieMapper {

    @Mapping(source = "category", target = "categoryDTO")
    MovieDTO movieToMovieDTO(Movie movie);
    @Mapping(source = "categoryDTO", target = "category")
    Movie movieDTOToMovie(MovieDTO movieDTO);
}

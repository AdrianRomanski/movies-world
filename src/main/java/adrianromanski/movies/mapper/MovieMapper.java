package adrianromanski.movies.mapper;

import adrianromanski.movies.domain.Movie;
import adrianromanski.movies.model.MovieDTO;
import org.mapstruct.Mapper;

@Mapper
public interface MovieMapper {

    MovieDTO movieToMovieDTO(Movie movie);

    Movie movieDTOToMovie(MovieDTO movieDTO);
}

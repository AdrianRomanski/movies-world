package adrianromanski.movies.services.movie;

import adrianromanski.movies.model.ActorDTO;
import adrianromanski.movies.model.MovieDTO;

import java.util.List;

public interface MovieService {

    List<MovieDTO> getAllMovies();

    MovieDTO getMovieByID(Long id);

    MovieDTO getMovieByName(String name);

    List<MovieDTO> findAllMoviesWithActor(String firstName, String lastName);


//    List<MovieDTO> getMoviesSortedByYear();
//
//    List<MovieDTO> getMoviesSortedByTime();
}

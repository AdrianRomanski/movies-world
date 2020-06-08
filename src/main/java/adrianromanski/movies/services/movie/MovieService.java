package adrianromanski.movies.services.movie;

import adrianromanski.movies.model.ActorDTO;
import adrianromanski.movies.model.MovieDTO;

import java.util.List;

public interface MovieService {

    //GET
    List<MovieDTO> getAllMovies();

    MovieDTO getMovieByID(Long id);

    MovieDTO getMovieByName(String name);

    List<MovieDTO> findAllMoviesWithActor(String firstName, String lastName);

    //POST
    MovieDTO createMovie(MovieDTO movieDTO);

    //PUT
    MovieDTO updateMovie(Long id, MovieDTO movieDTO);

    //DELETE
    void deleteMovieByID(Long id);
}

package adrianromanski.movies.services.movie;

import adrianromanski.movies.model.award.MovieAwardDTO;
import adrianromanski.movies.model.base_entity.MovieDTO;

import java.util.List;

public interface MovieService {

    //GET
    List<MovieDTO> getAllMovies();

    MovieDTO getMovieByID(Long id);

    MovieDTO getMovieByName(String name);

    List<MovieDTO> findAllMoviesWithActor(String firstName, String lastName);

    //POST
    MovieDTO createMovie(MovieDTO movieDTO);

    MovieAwardDTO addAward(Long id, MovieAwardDTO awardDTO);

    //PUT
    MovieDTO updateMovie(Long id, MovieDTO movieDTO);

    MovieAwardDTO updateAward(Long movieID, Long awardID, MovieAwardDTO awardDTO);

    //DELETE
    void deleteMovieByID(Long id);

    void deleteAwardByID(Long movieID, Long awardID);
}

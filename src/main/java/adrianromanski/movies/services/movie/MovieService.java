package adrianromanski.movies.services.movie;

import adrianromanski.movies.domain.base_entity.Movie;
import adrianromanski.movies.model.award.MovieAwardDTO;
import adrianromanski.movies.model.base_entity.MovieDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface MovieService {

    //GET
    List<MovieDTO> getAllMovies();

    Page<Movie> getAllMoviesPaged(Pageable pageable);

    Page<MovieDTO> getPageMovieDTO(Page<Movie> moviePage, Pageable pageable);

    Page<Movie> getAllMoviesForCategoryPaged(String name, Pageable pageable);

    MovieDTO getMovieByID(Long id);

    MovieDTO getMovieByName(String name);

    List<MovieDTO> findAllMoviesWithActor(String firstName, String lastName);

    List<MovieDTO> getMostFavouriteMovies();

    List<MovieDTO> getMostWatchedMovies();

    Map<Double, List<MovieDTO>> getMoviesByRating();

    //POST
    MovieDTO createMovie(MovieDTO movieDTO);

    MovieDTO addCategoryToMovie(Long movieID, Long categoryID);

//    MovieDTO createMovie(MovieDTO movieDTO, Long categoryID);

    MovieAwardDTO addAward(Long id, MovieAwardDTO awardDTO);

    //PUT
    MovieDTO updateMovie(Long id, MovieDTO movieDTO);

    MovieAwardDTO updateAward(Long movieID, Long awardID, MovieAwardDTO awardDTO);

    //PATCH
    MovieDTO updateMovieFields(Long id, MovieDTO movieDTO);

    //DELETE
    void deleteMovieByID(Long id);

    void deleteAwardByID(Long movieID, Long awardID);
}

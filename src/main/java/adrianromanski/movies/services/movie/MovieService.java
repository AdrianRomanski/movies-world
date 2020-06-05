package adrianromanski.movies.services.movie;

import adrianromanski.movies.model.MovieDTO;

import java.util.List;

public interface MovieService {

    List<MovieDTO> findAll();
}

package adrianromanski.movies.services;

import adrianromanski.movies.model.MovieDTO;

import java.util.List;

public interface MovieService {

    List<MovieDTO> findAll();
}

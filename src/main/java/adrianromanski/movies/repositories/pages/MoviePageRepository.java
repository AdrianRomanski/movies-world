package adrianromanski.movies.repositories.pages;

import adrianromanski.movies.domain.base_entity.Movie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;


public interface MoviePageRepository extends CrudRepository<Movie, Long> {

    Page<Movie> findAll(Pageable pageable);

    Optional<Page<Movie>> findAllByCategory_Name(String name, Pageable pageable);
}

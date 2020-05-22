package adrianromanski.movies.repositories;

import adrianromanski.movies.domain.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieRepository extends JpaRepository<Movie, Long> {
}

package adrianromanski.movies.repositories;

import adrianromanski.movies.domain.person.Director;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DirectorRepository extends JpaRepository<Director, Long> {
}

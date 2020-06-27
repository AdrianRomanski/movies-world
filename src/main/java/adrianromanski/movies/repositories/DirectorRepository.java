package adrianromanski.movies.repositories;

import adrianromanski.movies.domain.person.Director;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DirectorRepository extends JpaRepository<Director, Long> {

    Optional<Director> getDirectorByFirstNameAndAndLastName(String firstName, String lastName);
}

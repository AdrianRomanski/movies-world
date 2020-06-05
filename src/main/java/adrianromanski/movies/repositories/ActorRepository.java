package adrianromanski.movies.repositories;

import adrianromanski.movies.domain.Actor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ActorRepository extends JpaRepository<Actor, Long> {

    Optional<Actor> findByFirstNameAndAndLastName(String firstName, String lastName);
}

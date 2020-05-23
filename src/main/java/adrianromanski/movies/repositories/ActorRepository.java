package adrianromanski.movies.repositories;

import adrianromanski.movies.domain.Actor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ActorRepository extends JpaRepository<Actor, Long> {
}

package adrianromanski.movies.repositories.base_entity;

import adrianromanski.movies.domain.base_entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event, Long> {
}

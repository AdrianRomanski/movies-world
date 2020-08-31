package adrianromanski.movies.repositories.pages;

import adrianromanski.movies.domain.base_entity.Event;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

public interface EventPageRepository extends CrudRepository<Event, Long> {

    Page<Event> findAll(Pageable pageable);
}

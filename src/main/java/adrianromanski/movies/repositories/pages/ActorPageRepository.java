package adrianromanski.movies.repositories.pages;

import adrianromanski.movies.domain.person.Actor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

public interface ActorPageRepository extends CrudRepository<Actor, Long> {

    Page<Actor> findAll(Pageable pageable);

}

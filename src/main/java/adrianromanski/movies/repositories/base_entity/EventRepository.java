package adrianromanski.movies.repositories.base_entity;

import adrianromanski.movies.domain.base_entity.News;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<News, Long> {
}

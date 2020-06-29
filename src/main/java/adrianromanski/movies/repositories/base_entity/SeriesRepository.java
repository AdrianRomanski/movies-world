package adrianromanski.movies.repositories.base_entity;

import adrianromanski.movies.domain.base_entity.Series;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SeriesRepository extends JpaRepository<Series, Long> {

}

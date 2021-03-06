package adrianromanski.movies.repositories.base_entity;

import adrianromanski.movies.domain.base_entity.Episode;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EpisodeRepository extends JpaRepository<Episode, Long> {
}

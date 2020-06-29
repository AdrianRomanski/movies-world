package adrianromanski.movies.repositories.award;

import adrianromanski.movies.domain.award.Award;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AwardRepository extends JpaRepository<Award, Long> {
}

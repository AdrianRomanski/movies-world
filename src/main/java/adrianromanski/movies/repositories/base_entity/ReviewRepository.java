package adrianromanski.movies.repositories.base_entity;

import adrianromanski.movies.domain.review.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {
}

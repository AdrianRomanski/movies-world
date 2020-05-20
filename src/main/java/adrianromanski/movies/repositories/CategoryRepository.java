package adrianromanski.movies.repositories;

import adrianromanski.movies.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}

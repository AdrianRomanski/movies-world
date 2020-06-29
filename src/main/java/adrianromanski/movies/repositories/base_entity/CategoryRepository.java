package adrianromanski.movies.repositories.base_entity;

import adrianromanski.movies.domain.base_entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    Optional<Category> findByName(String name);
}

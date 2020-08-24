package adrianromanski.movies.repositories.paging;

import adrianromanski.movies.domain.base_entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

public interface CategoryDao extends CrudRepository<Category, Long> {

    Page<Category> findAll(Pageable pageable);
}

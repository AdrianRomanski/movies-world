package adrianromanski.movies.repositories.pages;

import adrianromanski.movies.domain.base_entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

@Component
public interface CategoryPage extends CrudRepository<Category, Long> {

    Page<Category> findAll(Pageable pageable);

}

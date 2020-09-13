package adrianromanski.movies.repositories.pages;

import adrianromanski.movies.domain.base_entity.News;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

public interface EventPageRepository extends CrudRepository<News, Long> {

    Page<News> findAll(Pageable pageable);
}

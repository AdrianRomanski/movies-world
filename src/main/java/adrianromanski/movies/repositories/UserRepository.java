package adrianromanski.movies.repositories;

import adrianromanski.movies.domain.person.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository  extends JpaRepository<User, Long> {
}

package adrianromanski.movies.domain;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


@Entity
@Getter
@NoArgsConstructor
public class User extends Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Builder
    public User(String firstName, String lastName, String gender, String username, String password) {
        super(firstName, lastName, gender, username, password);
    }
}

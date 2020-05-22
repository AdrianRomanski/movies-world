package adrianromanski.movies.domain;

import lombok.*;

import javax.persistence.Entity;


@Entity
@Getter
@Setter
@NoArgsConstructor
public class User extends Person {

    @Builder
    public User(Long id, String firstName, String lastName, String gender, String username, String password) {
        super(id, firstName, lastName, gender, username, password);
    }
}

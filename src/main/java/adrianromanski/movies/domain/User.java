package adrianromanski.movies.domain;

import lombok.*;

import javax.persistence.Entity;


@Entity
@Getter
@Setter
@NoArgsConstructor
public class User extends Person {

    private String username;
    private String password;

    @Builder
    public User(Long id, String firstName, String lastName, String gender, String username, String password) {
        super(id, firstName, lastName, gender);
        this.username = username;
        this.password = password;
    }
}

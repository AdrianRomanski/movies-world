package adrianromanski.movies.domain;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


@EqualsAndHashCode(callSuper = false)
@Entity
@Data
@NoArgsConstructor
public class User extends Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Builder
    public User(String firstName, String lastName, String gender) {
        super(firstName, lastName, gender);
    }
}

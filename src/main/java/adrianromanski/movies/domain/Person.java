package adrianromanski.movies.domain;

import lombok.*;

import javax.persistence.MappedSuperclass;


@MappedSuperclass
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Person{

    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private String gender;

}

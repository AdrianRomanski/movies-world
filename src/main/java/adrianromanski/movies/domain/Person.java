package adrianromanski.movies.domain;

import lombok.*;

import javax.persistence.MappedSuperclass;


@MappedSuperclass
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Person{

    private String firstName;
    private String lastName;
    private String gender;
}

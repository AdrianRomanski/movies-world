package adrianromanski.movies.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@MappedSuperclass
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PersonDTO {

    @NotEmpty
    @Size(min = 3, max = 20)
    private String firstName;
    @NotEmpty
    @Size(min = 3, max = 20)
    private String lastName;
    @NotEmpty
    @Size(min = 3, max = 10)
    private String gender;
}

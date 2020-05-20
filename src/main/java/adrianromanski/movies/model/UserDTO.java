package adrianromanski.movies.model;


import lombok.*;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class UserDTO  extends  PersonDTO {

    private Long id;

    @Builder
    public UserDTO(String firstName, String lastName, String gender,LocalDate dateOfBirth, Long id) {
        super(firstName, lastName, gender, dateOfBirth);
        this.id = id;
    }
}

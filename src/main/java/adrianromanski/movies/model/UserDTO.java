package adrianromanski.movies.model;

import lombok.*;

@Getter
@NoArgsConstructor
@Setter
public class UserDTO  extends  PersonDTO {

    @Builder
    public UserDTO(Long id, String firstName,  String lastName,  String gender, String username, String password) {
        super(id, firstName, lastName, gender, username, password);
    }
}

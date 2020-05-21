package adrianromanski.movies.model;

import lombok.*;

@Getter
@NoArgsConstructor
public class UserDTO  extends  PersonDTO {

    private Long id;

    @Builder
    public UserDTO(String firstName, String lastName, String username, String password, String gender) {
        super(firstName, lastName, username, password, gender);
    }
}

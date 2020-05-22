package adrianromanski.movies.model;

import lombok.*;

@Getter
@NoArgsConstructor
@Setter
public class UserDTO  extends  PersonDTO {

    private Long id;

    @Builder
    public UserDTO(String firstName, String lastName, String gender,String username, String password) {
        super(firstName, lastName, gender,username, password);
    }
}

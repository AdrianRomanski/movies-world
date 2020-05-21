package adrianromanski.movies.model;


import lombok.*;
import org.springframework.stereotype.Service;

@Getter
@Service
@NoArgsConstructor
public class UserDTO  extends  PersonDTO {

    private Long id;

    @Builder
    public UserDTO(String firstName, String lastName, String gender,Long id) {
        super(firstName, lastName, gender);
        this.id = id;
    }
}

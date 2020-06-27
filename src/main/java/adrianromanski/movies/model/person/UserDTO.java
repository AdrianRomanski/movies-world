package adrianromanski.movies.model.person;

import adrianromanski.movies.model.base_entity.MovieDTO;
import adrianromanski.movies.model.person.PersonDTO;
import lombok.*;

import javax.validation.constraints.NotEmpty;
import java.util.HashSet;
import java.util.Set;

@Getter
@NoArgsConstructor
@Setter
public class UserDTO  extends PersonDTO {

    @NotEmpty
    private String username;
    @NotEmpty
    private String password;

    @Builder
    public UserDTO(Long id, String firstName,  String lastName,  String gender, String username, String password,
                   Set<MovieDTO> favouriteMoviesDTO) {
        super(id, firstName, lastName, gender);
        this.username = username;
        this.password = password;
        if(favouriteMoviesDTO == null){ this.favouriteMoviesDTO = new HashSet<>();}
        else{ this.favouriteMoviesDTO = favouriteMoviesDTO; }
    }

    private Set<MovieDTO> favouriteMoviesDTO = new HashSet<>();
}

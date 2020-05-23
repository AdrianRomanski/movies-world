package adrianromanski.movies.model;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@Setter
public class ActorDTO extends PersonDTO{

    @Builder
    public ActorDTO(Long id, String firstName,  String lastName,  String gender,
                    List<MovieDTO> moviesDTO) {
        super(id, firstName, lastName, gender);
        this.moviesDTO = moviesDTO;
    }

    private List<MovieDTO> moviesDTO = new ArrayList<>();
}

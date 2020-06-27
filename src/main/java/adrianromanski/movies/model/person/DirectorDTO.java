package adrianromanski.movies.model.person;

import adrianromanski.movies.model.base_entity.MovieDTO;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class DirectorDTO extends PersonDTO {

    @Builder
    public DirectorDTO(Long id, String firstName, String lastName, String gender, List<MovieDTO> moviesDTO) {
        super(id, firstName, lastName, gender);
        if(this.moviesDTO == null) { this.moviesDTO = new ArrayList<>(); }
        else{ this.moviesDTO = moviesDTO; }
    }

    private List<MovieDTO> moviesDTO = new ArrayList<>();
}

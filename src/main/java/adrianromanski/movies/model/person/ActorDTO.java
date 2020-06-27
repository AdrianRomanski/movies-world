package adrianromanski.movies.model.person;

import adrianromanski.movies.model.base_entity.MovieDTO;
import adrianromanski.movies.model.award.ActorAwardDTO;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@Setter
public class ActorDTO extends PersonDTO {

    @Builder
    public ActorDTO(Long id, String firstName, String lastName, String gender,
                    List<MovieDTO> moviesDTO, List<ActorAwardDTO> awardsDTO) {
        super(id, firstName, lastName, gender);
        if(moviesDTO == null){ this.moviesDTO = new ArrayList<>();}
        else{ this.moviesDTO = moviesDTO; }
        if(awardsDTO == null){ this.awardsDTO = new ArrayList<>();}
        else{ this.awardsDTO = awardsDTO; }
    }

    private List<MovieDTO> moviesDTO = new ArrayList<>();
    private List<ActorAwardDTO> awardsDTO = new ArrayList<>();
}

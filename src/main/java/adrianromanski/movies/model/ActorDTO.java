package adrianromanski.movies.model;

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
public class ActorDTO extends PersonDTO{

    @Builder
    public ActorDTO(Long id, String firstName,  String lastName,  String gender,
                    List<MovieDTO> moviesDTO, List<ActorAwardDTO> awardsDTO) {
        super(id, firstName, lastName, gender);
        if(moviesDTO == null){this.moviesDTO = new ArrayList<>();}
        this.moviesDTO = moviesDTO;
        if(awardsDTO == null){this.awardsDTO = new ArrayList<>();}
        this.awardsDTO = awardsDTO;
    }

    private List<MovieDTO> moviesDTO = new ArrayList<>();
    private List<ActorAwardDTO> awardsDTO = new ArrayList<>();
}

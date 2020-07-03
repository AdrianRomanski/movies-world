package adrianromanski.movies.model.person;

import adrianromanski.movies.model.base_entity.MovieDTO;
import adrianromanski.movies.model.award.ActorAwardDTO;
import com.google.common.collect.ImmutableList;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@Setter
public class ActorDTO extends PersonDTO {

    /**
     * I have to use this kind of structure because otherwise i couldn't initialize mutable Collection
     * with @Singular annotation because it uses ImmutableList by default
     * @see Singular
     * @see ImmutableList
     */
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

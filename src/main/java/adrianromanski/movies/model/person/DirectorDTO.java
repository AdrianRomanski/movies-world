package adrianromanski.movies.model.person;

import adrianromanski.movies.domain.award.DirectorAward;
import adrianromanski.movies.model.base_entity.MovieDTO;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class DirectorDTO extends PersonDTO {

    @Builder
    public DirectorDTO(Long id, String firstName, String lastName, String gender,
                       List<MovieDTO> moviesDTO, Set<DirectorAward> awardsDTO) {
        super(id, firstName, lastName, gender);
        if(moviesDTO == null) { this.moviesDTO = new ArrayList<>(); }
        else{ this.moviesDTO = moviesDTO; }
        if(awardsDTO == null) { this.awardsDTO = new HashSet<>(); }
        else{ this.awardsDTO = awardsDTO; }
    }

    private List<MovieDTO> moviesDTO = new ArrayList<>();
    private Set<DirectorAward> awardsDTO = new HashSet<>();
}

package adrianromanski.movies.model.person;

import adrianromanski.movies.domain.award.DirectorAward;
import adrianromanski.movies.model.base_entity.MovieDTO;
import com.google.common.collect.ImmutableList;
import lombok.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class DirectorDTO extends PersonDTO {

    /**
     * I have to use this kind of structure because otherwise i couldn't initialize mutable Collection
     * with @Singular annotation because it uses ImmutableList by default
     * @see Singular
     * @see ImmutableList
     */
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

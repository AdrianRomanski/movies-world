package adrianromanski.movies.model.person;

import adrianromanski.movies.domain.award.DirectorAward;
import adrianromanski.movies.model.base_entity.MovieDTO;
import com.google.common.collect.ImmutableList;
import lombok.*;

import java.util.*;

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
        this.moviesDTO = Objects.requireNonNullElseGet(moviesDTO, ArrayList::new);
        this.awardsDTO = Objects.requireNonNullElseGet(awardsDTO, HashSet::new);
    }

    private List<MovieDTO> moviesDTO = new ArrayList<>();
    private Set<DirectorAward> awardsDTO = new HashSet<>();
}

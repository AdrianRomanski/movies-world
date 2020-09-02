package adrianromanski.movies.model.person;

import adrianromanski.movies.model.award.ActorAwardDTO;
import adrianromanski.movies.model.base_entity.MovieDTO;
import com.google.common.collect.ImmutableList;
import lombok.*;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@NoArgsConstructor
@Setter
public class ActorDTO extends PersonDTO {

    private int age;
    private String country;

    /**
     * I have to use this kind of structure because otherwise i couldn't initialize mutable Collection
     * with @Singular annotation because it uses ImmutableList by default
     * @see Singular
     * @see ImmutableList
     */
    @Builder
    public ActorDTO(Long id, String firstName, String lastName, String gender,
                    Byte[] image, LocalDate dateOfBirth, String country,
                    List<MovieDTO> moviesDTO, List<ActorAwardDTO> awardsDTO) {
        super(id, firstName, lastName, gender, dateOfBirth, image);
        this.country = country;
        this.moviesDTO = Objects.requireNonNullElseGet(moviesDTO, ArrayList::new);
        this.awardsDTO = Objects.requireNonNullElseGet(awardsDTO, ArrayList::new);
    }

    private List<MovieDTO> moviesDTO = new ArrayList<>();
    private List<ActorAwardDTO> awardsDTO = new ArrayList<>();

    public int getAge() {
        Period diff = Period.between(this.getDateOfBirth(),LocalDate.now());
        return diff.getYears();
    }
}

package adrianromanski.movies.model.person;

import adrianromanski.movies.model.base_entity.MovieDTO;
import adrianromanski.movies.model.review.MovieReviewDTO;
import com.google.common.collect.ImmutableList;
import lombok.*;

import javax.validation.constraints.NotEmpty;
import java.util.*;

@Getter
@NoArgsConstructor
@Setter
public class UserDTO  extends PersonDTO {

    @NotEmpty
    private String username;
    @NotEmpty
    private String password;

    /**
     * I have to use this kind of structure because otherwise i couldn't initialize mutable Collection
     * with @Singular annotation because it uses ImmutableList by default
     * @see Singular
     * @see ImmutableList
     */
    @Builder
    public UserDTO(Long id, String firstName,  String lastName,  String gender, String username, String password,
                     Set<MovieDTO> favouriteMoviesDTO, Set<MovieDTO> watchedMoviesDTO,
                     List<MovieReviewDTO> movieReviewsDTO) {
        super(id, firstName, lastName, gender);
        this.username = username;
        this.password = password;
        this.favouriteMoviesDTO = Objects.requireNonNullElseGet(favouriteMoviesDTO, HashSet::new);
        this.watchedMoviesDTO = Objects.requireNonNullElseGet(watchedMoviesDTO, HashSet::new);
        this.movieReviewsDTO = Objects.requireNonNullElseGet(movieReviewsDTO, ArrayList::new);
    }

    private Set<MovieDTO> favouriteMoviesDTO = new HashSet<>();
    private Set<MovieDTO> watchedMoviesDTO = new HashSet<>();
    private List<MovieReviewDTO> movieReviewsDTO = new ArrayList<>();

}

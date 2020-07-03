package adrianromanski.movies.model.person;

import adrianromanski.movies.model.base_entity.MovieDTO;
import adrianromanski.movies.model.review.MovieReviewDTO;
import com.google.common.collect.ImmutableList;
import lombok.*;

import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
        if(favouriteMoviesDTO == null){ this.favouriteMoviesDTO = new HashSet<>();}
        else{ this.favouriteMoviesDTO = favouriteMoviesDTO; }
        if(watchedMoviesDTO == null){ this.watchedMoviesDTO = new HashSet<>();}
        else{ this.watchedMoviesDTO = watchedMoviesDTO; }
        if(movieReviewsDTO == null){ this.movieReviewsDTO = new ArrayList<>();}
        else{ this.movieReviewsDTO = movieReviewsDTO; }
    }

    private Set<MovieDTO> favouriteMoviesDTO = new HashSet<>();
    private Set<MovieDTO> watchedMoviesDTO = new HashSet<>();
    private List<MovieReviewDTO> movieReviewsDTO = new ArrayList<>();

}

package adrianromanski.movies.model.review;

import adrianromanski.movies.model.base_entity.MovieDTO;
import adrianromanski.movies.model.person.UserDTO;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class MovieReviewDTO extends ReviewDTO {

    @Builder
    public MovieReviewDTO(Long id, String name, String description, String imageURL, LocalDate date, Integer score, Long userID, UserDTO userDTO, MovieDTO movieDTO) {
        super(id, name, description, imageURL, date, score, userID);
        this.userDTO = userDTO;
        this.movieDTO = movieDTO;
    }

    private UserDTO userDTO;
    private MovieDTO movieDTO;
}

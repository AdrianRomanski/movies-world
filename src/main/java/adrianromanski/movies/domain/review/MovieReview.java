package adrianromanski.movies.domain.review;

import adrianromanski.movies.domain.base_entity.Movie;
import adrianromanski.movies.domain.person.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class MovieReview extends Review{

    @Builder
    public MovieReview(Long id, String name, String description, Byte[] image, LocalDate date, Integer score, Long userID,
                       User user, Movie movie) {
        super(id, name, description, image, date, score, userID);
        this.user = user;
        this.movie = movie;
    }

    @ManyToOne
    private User user;

    @ManyToOne
    private Movie movie;


}

package adrianromanski.movies.domain.award;

import adrianromanski.movies.domain.base_entity.Movie;
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
public class MovieAward extends Award {

    @Builder
    public MovieAward(LocalDate date, String country, String awardCategory, Movie movie) {
        super(date, country, awardCategory);
        this.movie = movie;
    }

    @ManyToOne
    private Movie movie;
}

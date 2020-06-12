package adrianromanski.movies.model.award;

import adrianromanski.movies.model.MovieDTO;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class MovieAwardDTO extends AwardDTO{

    @Builder
    public MovieAwardDTO(LocalDate date, String country, String awardCategory, MovieDTO movieDTO) {
        super(date, country, awardCategory);
        this.movieDTO = movieDTO;
    }

    private MovieDTO movieDTO;
}

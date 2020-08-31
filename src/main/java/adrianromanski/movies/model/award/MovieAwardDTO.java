package adrianromanski.movies.model.award;

import adrianromanski.movies.model.base_entity.MovieDTO;
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
    public MovieAwardDTO(Long id, String name, String description, Byte[] image, LocalDate date,
                         String country, String awardCategory, MovieDTO movieDTO) {
        super(id, name, description, image, date, country, awardCategory);
        this.movieDTO = movieDTO;
    }

    private MovieDTO movieDTO;
}

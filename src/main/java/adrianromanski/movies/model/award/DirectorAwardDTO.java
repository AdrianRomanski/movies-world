package adrianromanski.movies.model.award;

import adrianromanski.movies.model.person.DirectorDTO;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class DirectorAwardDTO extends AwardDTO {

    @Builder
    public DirectorAwardDTO(LocalDate date, String country, String awardCategory, DirectorDTO directorDTO) {
        super(date, country, awardCategory);
        this.directorDTO = directorDTO;
    }

    private DirectorDTO directorDTO;
}

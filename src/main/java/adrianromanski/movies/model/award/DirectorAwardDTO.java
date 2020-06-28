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
    public DirectorAwardDTO(Long id, String name, String description, String imageURL, LocalDate date,
                            String country, String awardCategory, DirectorDTO directorDTO) {
        super(id, name, description, imageURL, date, country, awardCategory);
        this.directorDTO = directorDTO;
    }

    private DirectorDTO directorDTO;
}

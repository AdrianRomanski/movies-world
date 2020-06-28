package adrianromanski.movies.model.award;

import adrianromanski.movies.model.person.ActorDTO;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class ActorAwardDTO extends AwardDTO {

    @Builder
    public ActorAwardDTO(Long id, String name, String description, String imageURL,
                         LocalDate date, String country, String awardCategory, ActorDTO actorDTO) {
        super(id, name, description, imageURL, date, country, awardCategory);
        this.actorDTO = actorDTO;
    }

    private ActorDTO actorDTO;
}

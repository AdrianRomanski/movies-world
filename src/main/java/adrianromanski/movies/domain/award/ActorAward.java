package adrianromanski.movies.domain.award;

import adrianromanski.movies.domain.person.Actor;
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
public class ActorAward extends Award {

    @Builder
    public ActorAward(Long id, String name, String description, Byte[] image,
                         LocalDate date, String country, String awardCategory, Actor actor) {
        super(id, name, description, image, date, country, awardCategory);
        this.actor = actor;
    }

    @ManyToOne
    private Actor actor;

}

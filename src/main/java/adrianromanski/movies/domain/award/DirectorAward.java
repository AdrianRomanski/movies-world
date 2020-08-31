package adrianromanski.movies.domain.award;

import adrianromanski.movies.domain.person.Director;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class DirectorAward extends Award {

    @Builder
    public DirectorAward(Long id, String name, String description, Byte[] image,
                      LocalDate date, String country, String awardCategory, Director director) {
        super(id, name, description, image, date, country, awardCategory);
        this.director = director;
    }

    @ManyToOne
    private Director director;
}

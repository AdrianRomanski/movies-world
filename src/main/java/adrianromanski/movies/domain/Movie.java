package adrianromanski.movies.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class Movie extends BaseEntity {

    private Long minutes;

    @Builder
    public Movie(Long id, String name, String description, String imageURL, Long minutes,
                 Category category, List<Actor> actors) {
        super(id, name, description, imageURL);
        this.minutes = minutes;
        this.category = category;
        this.actors = actors;
    }

    @ManyToOne
    private Category category;

    @ManyToMany(mappedBy = "movies")
    private List<Actor> actors;
}

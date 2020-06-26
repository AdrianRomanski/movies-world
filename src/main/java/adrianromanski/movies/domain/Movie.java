package adrianromanski.movies.domain;

import adrianromanski.movies.domain.award.MovieAward;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class Movie extends BaseEntity {

    private Long minutes;

    @Builder
    public Movie(Long id, String name, String description, String imageURL, Long minutes,
                 Category category, List<Actor> actors, List<MovieAward> awards) {
        super(id, name, description, imageURL);
        this.minutes = minutes;
        this.category = category;
        this.actors = actors;
        this.awards = awards;
    }

    @OneToMany(mappedBy = "movie")
    private List<MovieAward> awards = new ArrayList<>();

    public Optional<MovieAward> getAwardOptional(Long id) {
        return this.getAwards().stream()
                .filter(a -> a.getId().equals(id))
                .findAny();
    }

    @ManyToOne
    private Category category;

    @ManyToMany(mappedBy = "movies")
    private List<Actor> actors  = new ArrayList<>();
}

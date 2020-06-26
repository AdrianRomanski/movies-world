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
import java.util.*;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class Movie extends BaseEntity {

    private Long minutes;

    @Builder
    public Movie(Long id, String name, String description, String imageURL, Long minutes,
                 Category category, List<Actor> actors, List<MovieAward> awards, Set<User> users) {
        super(id, name, description, imageURL);
        this.minutes = minutes;
        this.category = category;
        if(actors == null){this.actors = new ArrayList<>();}
        this.actors = actors;
        if(awards == null){this.actors = new ArrayList<>();}
        this.awards = awards;
        if(users == null){this.users = new HashSet<>();}
        this.users = users;
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
    private List<Actor> actors = new ArrayList<>();

    @ManyToMany(mappedBy = "favouriteMovies")
    private Set<User> users = new HashSet<>();
}

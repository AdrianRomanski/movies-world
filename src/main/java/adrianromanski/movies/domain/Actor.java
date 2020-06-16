package adrianromanski.movies.domain;


import adrianromanski.movies.domain.award.ActorAward;
import adrianromanski.movies.domain.award.Award;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class Actor extends Person {

    @Builder
    public Actor(Long id, String firstName, String lastName, String gender,
                 List<Movie> movies, List<ActorAward> awards) {
        super(id, firstName, lastName, gender);
        this.movies = movies;
        this.awards = awards;
    }

    @OneToMany(mappedBy = "actor")
    private List<ActorAward> awards = new ArrayList<>();


    public Optional<ActorAward> getAwardOptional(Long awardID) {
        return this.getAwards().stream()
                .filter(award -> award.getId().equals(awardID))
                .findAny();
    }

    @ManyToMany
    @JoinTable(name = "actor_movies", joinColumns = @JoinColumn(name = "actor_id"),
    inverseJoinColumns = @JoinColumn(name = "movie_id"))
    private List<Movie> movies = new ArrayList<>();
}

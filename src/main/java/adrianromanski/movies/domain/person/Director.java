package adrianromanski.movies.domain.person;

import adrianromanski.movies.domain.award.DirectorAward;
import adrianromanski.movies.domain.base_entity.Movie;
import com.google.common.collect.ImmutableList;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Director extends Person {


    /**
     * I have to use this kind of structure because otherwise i couldn't initialize mutable Collection
     * with @Singular annotation because it uses ImmutableList by default
     * @see Singular
     * @see ImmutableList
     */
    @Builder
    public Director(Long id, String firstName, String lastName, String gender,
                    List<Movie> movies, Set<DirectorAward> awards) {
        super(id, firstName, lastName, gender);
        this.movies = Objects.requireNonNullElseGet(movies, ArrayList::new);
        this.awards = Objects.requireNonNullElseGet(awards, HashSet::new);
    }

    @OneToMany(mappedBy = "director")
    private List<Movie> movies = new ArrayList<>();

    @OneToMany(mappedBy = "director")
    private Set<DirectorAward> awards = new HashSet<>();

    public Optional<DirectorAward> getAwardOptional(Long id) {
        return this.awards
                .stream()
                .filter(a -> a.getId().equals(id))
                .findAny();
    }

}

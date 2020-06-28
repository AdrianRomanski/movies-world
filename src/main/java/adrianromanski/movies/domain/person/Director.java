package adrianromanski.movies.domain.person;

import adrianromanski.movies.domain.award.DirectorAward;
import adrianromanski.movies.domain.base_entity.Movie;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Director extends Person {

    @Builder
    public Director(Long id, String firstName, String lastName, String gender,
                    List<Movie> movies, Set<DirectorAward> awards) {
        super(id, firstName, lastName, gender);
        if(movies == null) { this.movies = new ArrayList<>(); }
        else{ this.movies = movies; }
        if(awards == null) { this.awards = new HashSet<>(); }
        else{ this.awards = awards; }
    }

    @OneToMany(mappedBy = "director")
    private List<Movie> movies = new ArrayList<>();

    @OneToMany(mappedBy = "director")
    private Set<DirectorAward> awards = new HashSet<>();

}

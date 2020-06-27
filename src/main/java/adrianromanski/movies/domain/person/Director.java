package adrianromanski.movies.domain.person;

import adrianromanski.movies.domain.base_entity.Movie;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Director extends Person {

    @Builder
    public Director(Long id, String firstName, String lastName, String gender, List<Movie> movies) {
        super(id, firstName, lastName, gender);
        if(this.movies == null) { this.movies = new ArrayList<>(); }
        else{ this.movies = movies; }
    }

    @OneToMany
    private List<Movie> movies = new ArrayList<>();
}

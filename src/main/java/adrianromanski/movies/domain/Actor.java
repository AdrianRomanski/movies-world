package adrianromanski.movies.domain;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class Actor extends Person {

    @Builder
    public Actor(Long id, String firstName, String lastName, String gender,
                 List<Movie> movies) {
        super(id, firstName, lastName, gender);
        this.movies = movies;
    }

    @ManyToMany()
    @JoinTable(name = "actor_movies", joinColumns = @JoinColumn(name = "actor_id"),
    inverseJoinColumns = @JoinColumn(name = "movie_id"))
    private List<Movie> movies = new ArrayList<>();
}

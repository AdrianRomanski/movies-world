package adrianromanski.movies.domain;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import java.util.HashSet;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;


@Entity
@Getter
@Setter
@NoArgsConstructor
public class User extends Person {

    private String username;
    private String password;

    @Builder
    public User(Long id, String firstName, String lastName, String gender, String username, String password,
                Set<Movie> favouriteMovies) {
        super(id, firstName, lastName, gender);
        this.username = username;
        this.password = password;
        if(favouriteMovies == null){this.favouriteMovies = new HashSet<>();}
        this.favouriteMovies = favouriteMovies;

    }


    @ManyToMany
    @JoinTable(name = "user_movies", joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "movie_id"))
    private Set<Movie> favouriteMovies = new HashSet<>();
}

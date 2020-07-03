package adrianromanski.movies.domain.person;

import adrianromanski.movies.domain.base_entity.Movie;
import adrianromanski.movies.domain.review.MovieReview;
import com.google.common.collect.ImmutableList;
import lombok.*;

import javax.persistence.*;
import java.util.*;


@Entity
@Getter
@Setter
@NoArgsConstructor
public class User extends Person {

    private String username;
    private String password;

    /**
     * I have to use this kind of structure because otherwise i couldn't initialize mutable Collection
     * with @Singular annotation because it uses ImmutableList by default
     * @see Singular
     * @see ImmutableList
     */
    @Builder
    public User(Long id, String firstName, String lastName, String gender, String username, String password,
                Set<Movie> favouriteMovies,  Set<Movie> watchedMovies, List<MovieReview> movieReviews) {
        super(id, firstName, lastName, gender);
        this.username = username;
        this.password = password;
        if (favouriteMovies == null) { this.favouriteMovies = new HashSet<>(); }
        else { this.favouriteMovies = favouriteMovies; }
        if (watchedMovies == null) { this.watchedMovies = new HashSet<>(); }
        else { this.watchedMovies = watchedMovies; }
        if (movieReviews == null) { this.movieReviews = new ArrayList<>(); }
        else { this.movieReviews = movieReviews; }
    }

    @OneToMany(mappedBy = "movie")
    private List<MovieReview> movieReviews = new ArrayList<>();

    public Optional<MovieReview> getMovieReviewOptional(Long reviewID) {
        return this.getMovieReviews().stream()
                .filter(m -> m.getId().equals(reviewID))
                .findAny();
    }


    @ManyToMany
    @JoinTable(name = "user_favourite_movies", joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "favourite_movie_id"))
    private Set<Movie> favouriteMovies = new HashSet<>();

    public Optional<Movie> getFavMovieOptional(Long movieID) {
        return this.getFavouriteMovies().stream()
                .filter(m -> m.getId().equals(movieID))
                .findAny();
    }

    @ManyToMany
    @JoinTable(name = "user_watched_movies", joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "watched_movie_id"))
    private Set<Movie> watchedMovies = new HashSet<>();

    public Optional<Movie> getWatchedMovieOptional(Long movieID) {
        return this.getWatchedMovies().stream()
                .filter(m -> m.getId().equals(movieID))
                .findAny();
    }


}

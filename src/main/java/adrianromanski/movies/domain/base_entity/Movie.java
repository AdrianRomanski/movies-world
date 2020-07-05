package adrianromanski.movies.domain.base_entity;

import adrianromanski.movies.domain.award.MovieAward;
import adrianromanski.movies.domain.person.Actor;
import adrianromanski.movies.domain.person.Director;
import adrianromanski.movies.domain.person.User;
import adrianromanski.movies.domain.review.MovieReview;
import com.google.common.collect.ImmutableList;
import lombok.*;

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
    private Double avgRating;


    /**
     * I have to use this kind of structure because otherwise i couldn't initialize mutable Collection
     * with @Singular annotation because it uses ImmutableList by default
     * @see Singular
     * @see ImmutableList
     */
    @Builder
    public Movie(Long id, String name, String description, String imageURL, Long minutes,
                 Category category, Director director,
                 List<Actor> actors, List<MovieAward> awards, List<MovieReview> reviews,
                 Set<User> userFavourites, Set<User> userWatched) {
        super(id, name, description, imageURL);
        this.minutes = minutes;
        this.category = category;
        this.director = director;
        if(actors == null){ this.actors = new ArrayList<>();}
        else { this.actors = actors; }
        if(awards == null){ this.awards = new ArrayList<>();}
        else { this.awards = awards; }
        if(reviews == null){ this.reviews = new ArrayList<>();}
        else { this.reviews = reviews; }
        if(userFavourites == null){ this.userFavourites = new HashSet<>();}
        else { this.userFavourites = userFavourites; }
        if(userFavourites == null){ this.userWatched = new HashSet<>();}
        else { this.userWatched = userWatched; }
    }

    @OneToMany(mappedBy = "movie")
    private List<MovieAward> awards = new ArrayList<>();

    public Optional<MovieAward> getAwardOptional(Long id) {
        return this.getAwards().stream()
                .filter(a -> a.getId().equals(id))
                .findAny();
    }

    @OneToMany(mappedBy = "movie")
    private List<MovieReview> reviews = new ArrayList<>();

    @ManyToOne
    private Category category;

    @ManyToOne
    private Director director;

    @ManyToMany(mappedBy = "movies")
    private List<Actor> actors = new ArrayList<>();

    @ManyToMany(mappedBy = "favouriteMovies")
    private Set<User> userFavourites = new HashSet<>();

    @ManyToMany(mappedBy = "watchedMovies")
    private Set<User> userWatched = new HashSet<>();
}

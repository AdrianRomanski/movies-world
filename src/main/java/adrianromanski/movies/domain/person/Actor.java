package adrianromanski.movies.domain.person;


import adrianromanski.movies.domain.award.ActorAward;
import adrianromanski.movies.domain.base_entity.Movie;
import com.google.common.collect.ImmutableList;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class Actor extends Person {

//    private Double rating;
    private int age;
    private String country;

    /**
     * I have to use this kind of structure because otherwise i couldn't initialize mutable Collection
     * with @Singular annotation because it uses ImmutableList by default
     *
     * @see Singular
     * @see ImmutableList
     */
    @Builder
    public Actor(Long id, String firstName, String lastName, String gender,
                 Byte[] image, LocalDate dateOfBirth, String country,
                 List<Movie> movies, List<ActorAward> awards) {
        super(id, firstName, lastName, gender, image, dateOfBirth);
        this.country = country;
        this.movies = Objects.requireNonNullElseGet(movies, ArrayList::new);
        this.awards = Objects.requireNonNullElseGet(awards, ArrayList::new);
    }

    @OneToMany(mappedBy = "actor")
    private List<ActorAward> awards = new ArrayList<>();

    public Optional<ActorAward> getAwardOptional(Long awardID) {
        return this.getAwards().stream()
                .filter(award -> award.getId().equals(awardID))
                .findAny();
    }

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "actor_movies", joinColumns = @JoinColumn(name = "actor_id"),
            inverseJoinColumns = @JoinColumn(name = "movie_id"))
    private List<Movie> movies = new ArrayList<>();

    public int getAge() {
        Period diff = Period.between(this.getDateOfBirth(),LocalDate.now());
        return diff.getYears();
    }
}

    //    public  Double getRating() {
//        ArrayList<Integer> scores = new ArrayList<>();
//        this.getMovies()
//                .forEach(m -> m.getReviews()
//                        .forEach(r -> scores.add(r.getScore())));
//        return (double) scores.stream().reduce(0, Integer::sum) / scores.size();
//    }


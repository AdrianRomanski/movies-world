package adrianromanski.movies.model.base_entity;

import adrianromanski.movies.domain.review.MovieReview;
import adrianromanski.movies.domain.review.Review;
import adrianromanski.movies.exceptions.EmptyListException;
import adrianromanski.movies.model.award.MovieAwardDTO;
import adrianromanski.movies.model.person.ActorDTO;
import adrianromanski.movies.model.person.DirectorDTO;
import adrianromanski.movies.model.person.UserDTO;
import com.google.common.collect.ImmutableList;
import lombok.*;

import java.util.*;

@Getter
@NoArgsConstructor
@Setter
public class MovieDTO extends BaseEntityDTO{

    private Long minutes;

    /**
     * I have to use this kind of structure because otherwise i couldn't initialize mutable Collection
     * with @Singular annotation because it uses ImmutableList by default
     * @see Singular
     * @see ImmutableList
     */
    @Builder
    public MovieDTO(Long id,  String name,  String description, String imageURL, Long minutes,
                    CategoryDTO categoryDTO, DirectorDTO directorDTO,
                    List<ActorDTO> actorsDTO, List<MovieAwardDTO> awardsDTO, List<MovieReview> reviewsDTO,
                    Set<UserDTO> userFavouritesDTO, Set<UserDTO> userWatchedDTO) {
        super(id, name, description, imageURL);
        this.minutes = minutes;
        this.categoryDTO = categoryDTO;
        this.directorDTO = directorDTO;
        this.actorsDTO = Objects.requireNonNullElseGet(actorsDTO, ArrayList::new);
        if(awardsDTO == null){ this.awardsDTO = new ArrayList<>();}
        else{ this.awardsDTO = awardsDTO;}
        if(reviewsDTO == null){ this.reviewsDTO = new ArrayList<>();}
        else{ this.reviewsDTO = reviewsDTO;}
        this.userFavouritesDTO = Objects.requireNonNullElseGet(userFavouritesDTO, HashSet::new);
        this.userWatchedDTO = Objects.requireNonNullElseGet(userWatchedDTO, HashSet::new);
    }

    private CategoryDTO categoryDTO;
    private DirectorDTO directorDTO;
    private List<ActorDTO> actorsDTO = new ArrayList<>();
    private List<MovieAwardDTO> awardsDTO = new ArrayList<>();
    private List<MovieReview> reviewsDTO = new ArrayList<>();

    public Double getAvgRating() {
        return getReviewsDTO().stream()
                .mapToDouble(Review::getScore)
                .average()
                .orElseThrow(EmptyListException::new);
    }

    private Set<UserDTO> userFavouritesDTO = new HashSet<>();
    private Set<UserDTO> userWatchedDTO = new HashSet<>();
}

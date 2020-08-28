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

import javax.persistence.Lob;
import java.util.*;

@Setter
@Getter
@NoArgsConstructor
public class MovieDTO extends BaseEntityDTO{

    private Long time;
    @Lob
    private Byte[] image;
    private Integer year;
    private String country;

    /**
     * I have to use this kind of structure because otherwise i couldn't initialize mutable Collection
     * with @Singular annotation because it uses ImmutableList by default
     * @see Singular
     * @see ImmutableList
     */
    @Builder
    public MovieDTO(Long id,  String name,  String description, String imageURL, Long time, Byte[] image, Integer year, String country,
                    CategoryDTO categoryDTO, DirectorDTO directorDTO,
                    List<ActorDTO> actorsDTO, List<MovieAwardDTO> awardsDTO, List<MovieReview> reviewsDTO,
                    Set<UserDTO> userFavouritesDTO, Set<UserDTO> userWatchedDTO) {
        super(id, name, description, imageURL);
        this.time = time;
        this.image = image;
        this.year = year;
        this.country = country;
        this.categoryDTO = categoryDTO;
        this.directorDTO = directorDTO;
        this.actorsDTO = Objects.requireNonNullElseGet(actorsDTO, ArrayList::new);
        this.awardsDTO = Objects.requireNonNullElseGet(awardsDTO, ArrayList::new);
        this.reviewsDTO = Objects.requireNonNullElseGet(reviewsDTO, ArrayList::new);
        this.userFavouritesDTO = Objects.requireNonNullElseGet(userFavouritesDTO, HashSet::new);
        this.userWatchedDTO = Objects.requireNonNullElseGet(userWatchedDTO, HashSet::new);
    }

    private CategoryDTO categoryDTO;
    private DirectorDTO directorDTO;
    private List<ActorDTO> actorsDTO = new ArrayList<>();
    private List<MovieAwardDTO> awardsDTO = new ArrayList<>();
    private List<MovieReview> reviewsDTO = new ArrayList<>();
    private Set<UserDTO> userFavouritesDTO = new HashSet<>();
    private Set<UserDTO> userWatchedDTO = new HashSet<>();

    public Double getAvgRating() {
        return getReviewsDTO().stream()
                .mapToDouble(Review::getScore)
                .average()
                .orElseThrow(EmptyListException::new);
    }
}

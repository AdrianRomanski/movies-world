package adrianromanski.movies.mapper.review;

import adrianromanski.movies.domain.review.MovieReview;
import adrianromanski.movies.model.review.MovieReviewDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel="spring")
public interface MovieReviewMapper {

    @Mappings({
        @Mapping(source = "user", target = "userDTO"),
        @Mapping(source = "movie", target = "movieDTO")
    })
    MovieReviewDTO reviewToReviewDTO(MovieReview review);

    @Mappings({
        @Mapping(source = "userDTO", target = "user"),
        @Mapping(source = "movieDTO", target = "movie")
    })
    MovieReview reviewDTOToReview(MovieReviewDTO reviewDTO);
}

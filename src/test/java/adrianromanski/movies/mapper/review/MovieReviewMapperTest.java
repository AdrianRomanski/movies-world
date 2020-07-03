package adrianromanski.movies.mapper.review;

import adrianromanski.movies.domain.review.MovieReview;
import adrianromanski.movies.model.review.MovieReviewDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

class MovieReviewMapperTest {

    public static final LocalDate DATE = LocalDate.now();
    public static final String DESCRIPTION = "Desc";
    public static final String NAME = "Best";
    public static final long USER_ID = 22L;
    public static final int SCORE = 12;
    public static final String IMAGE_URL = "hehe.jpg";
    public static final long ID = 1L;

    MovieReviewMapper mapper = new MovieReviewMapperImpl();

    @Test
    void reviewToReviewDTO() {
        MovieReview review = MovieReview.builder().date(DATE).description(DESCRIPTION).id(ID).name(NAME).userID(USER_ID).score(SCORE).imageURL(IMAGE_URL).build();

        MovieReviewDTO reviewDTO = mapper.reviewToReviewDTO(review);

        Assertions.assertEquals(reviewDTO.getDate(), DATE);
        Assertions.assertEquals(reviewDTO.getDescription(), DESCRIPTION);
        Assertions.assertEquals(reviewDTO.getId(), ID);
        Assertions.assertEquals(reviewDTO.getName(), NAME);
        Assertions.assertEquals(reviewDTO.getUserID(), USER_ID);
        Assertions.assertEquals(reviewDTO.getScore(), SCORE);
        Assertions.assertEquals(reviewDTO.getImageURL(), IMAGE_URL);
    }

    @Test
    void reviewDTOToReview() {
        MovieReviewDTO reviewDTO = MovieReviewDTO.builder().date(DATE).description(DESCRIPTION).id(ID).name(NAME).userID(USER_ID).score(SCORE).imageURL(IMAGE_URL).build();

        MovieReview review = mapper.reviewDTOToReview(reviewDTO);

        Assertions.assertEquals(review.getDate(), DATE);
        Assertions.assertEquals(review.getDescription(), DESCRIPTION);
        Assertions.assertEquals(review.getId(), ID);
        Assertions.assertEquals(review.getName(), NAME);
        Assertions.assertEquals(review.getUserID(), USER_ID);
        Assertions.assertEquals(review.getScore(), SCORE);
        Assertions.assertEquals(review.getImageURL(), IMAGE_URL);
    }

}
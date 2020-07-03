package adrianromanski.movies.services.user;

import adrianromanski.movies.model.person.UserDTO;
import adrianromanski.movies.model.review.MovieReviewDTO;

import java.util.List;

public interface UserService {

    // GET
    List<UserDTO> getAllUsers();

    // POST
    UserDTO createUser(UserDTO userDTO);

    UserDTO addFavouriteMovie(Long userID, Long movieID);

    UserDTO addWatchedMovie(Long userID, Long movieID);

    MovieReviewDTO addMovieReview(Long userID, Long movieID, MovieReviewDTO reviewDTO);

    // PUT
    UserDTO updateUser(Long id, UserDTO userDTO);

    MovieReviewDTO updateMovieReview(Long userID, Long reviewID, MovieReviewDTO reviewDTO);

    //DELETE
    void deleteUser(Long id);

    void deleteFavouriteMovie(Long userID, Long movieID);

    void deleteWatchedMovie(Long userID, Long movieID);

    void deleteMovieReview(Long userID, Long reviewID);

}

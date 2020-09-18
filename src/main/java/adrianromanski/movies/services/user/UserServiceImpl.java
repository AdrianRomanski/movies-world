package adrianromanski.movies.services.user;

import adrianromanski.movies.aspects.creation_log.LogCreation;
import adrianromanski.movies.domain.base_entity.Movie;
import adrianromanski.movies.domain.person.User;
import adrianromanski.movies.domain.review.MovieReview;
import adrianromanski.movies.domain.review.Review;
import adrianromanski.movies.exceptions.ResourceNotFoundException;
import adrianromanski.movies.mapper.person.UserMapper;
import adrianromanski.movies.mapper.review.MovieReviewMapper;
import adrianromanski.movies.model.person.UserDTO;
import adrianromanski.movies.model.review.MovieReviewDTO;
import adrianromanski.movies.repositories.base_entity.MovieRepository;
import adrianromanski.movies.repositories.base_entity.ReviewRepository;
import adrianromanski.movies.repositories.person.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.stream.Collectors.toList;


@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final MovieRepository movieRepository;
    private final ReviewRepository reviewRepository;
    private final UserMapper userMapper;
    private final MovieReviewMapper reviewMapper;



    /**
     * User, Admin, Moderator
     * @return List with all Users
     */
    @Override
    public List<UserDTO> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(userMapper::userToUserDTO)
                .collect(toList());
    }

    /**
     * User(Only it's own account), Admin, Moderator
     * @param userDTO Creates new user
     * @return User if successfully saved to Database
     */
    @Override
    @LogCreation
    public UserDTO createUser(UserDTO userDTO) {
        User user = userMapper.userDTOToUser(userDTO);
        userRepository.save(user);
        return userMapper.userToUserDTO(user);
    }


    /**
     * @param userID of the User we want to Add favourite movie
     * @param movieID of the movie we want to Add
     * @throws ResourceNotFoundException if User or Movie not found
     * @return User if successfully added
     */
    @Override
    public UserDTO addFavouriteMovie(Long userID, Long movieID) {
        User user = userRepository.findById(userID)
                .orElseThrow(() -> new ResourceNotFoundException(userID, User.class));
        Movie movie = movieRepository.findById(movieID)
                .orElseThrow(() -> new ResourceNotFoundException(movieID, Movie.class));
        user.getFavouriteMovies().add(movie);
        movie.getUserFavourites().add(user);
        userRepository.save(user);
        movieRepository.save(movie);
        return userMapper.userToUserDTO(user);
    }


    /**
     * @param userID Of the User we want to add watched movie
     * @param movieID of the movie we want to add
     * @throws ResourceNotFoundException if not found
     * @return UserDTO if successfully added
     */
    @Override
    public UserDTO addWatchedMovie(Long userID, Long movieID) {
        User user = userRepository.findById(userID)
                .orElseThrow(() -> new ResourceNotFoundException(userID, User.class));
        Movie movie = movieRepository.findById(movieID)
                .orElseThrow(() ->  new ResourceNotFoundException(movieID, Movie.class));
        user.getWatchedMovies().add(movie);
        movie.getUserWatched().add(user);
        userRepository.save(user);
        movieRepository.save(movie);
        return userMapper.userToUserDTO(user);
    }


    /**
     * Adding rating to movie and score to actors
     * @param userID ID of the User
     * @param movieID ID of the Movie
     * @param reviewDTO Body
     * @return MovieReviewDTO if successfully saved to repositories
     * @throws ResourceNotFoundException if not found
     */
    @Override
    public MovieReviewDTO addMovieReview(Long userID, Long movieID, MovieReviewDTO reviewDTO) {
        User user = userRepository.findById(userID)
                .orElseThrow(() -> new ResourceNotFoundException(userID, User.class));
        Movie movie = movieRepository.findById(movieID)
                .orElseThrow(() -> new ResourceNotFoundException(movieID, Movie.class));
        MovieReview review = reviewMapper.reviewDTOToReview(reviewDTO);
        movie.getReviews().add(review);
        user.getMovieReviews().add(review);
        review.setMovie(movie);
        review.setUser(user);
        userRepository.save(user);
        movieRepository.save(movie);
        reviewRepository.save(review);
        return reviewMapper.reviewToReviewDTO(review);
    }


    /**
     * User(Only it's own account), Admin, Moderator
     * @param id of the User to update
     * @param userDTO object for updating User Entity
     * @return User if successfully updated
     * @throws ResourceNotFoundException if not found
     */
    @Override
    public UserDTO updateUser(Long id, UserDTO userDTO) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(id, User.class));
        User updatedUser = userMapper.userDTOToUser(userDTO);
        updatedUser.setId(id);
        user.getFavouriteMovies()
                .forEach(m -> updatedUser.getFavouriteMovies().add(m));
        userRepository.save(updatedUser);
        return userMapper.userToUserDTO(updatedUser);
    }


    /**
     * @param userID ID of the User
     * @param reviewID ID of the Review
     * @param reviewDTO Body
     * @throws ResourceNotFoundException if not found
     * @return MovieReviewDTO if succesfully updated
     */
    @Override
    public MovieReviewDTO updateMovieReview(Long userID, Long reviewID, MovieReviewDTO reviewDTO) {
        User user = userRepository.findById(userID)
                .orElseThrow(() -> new ResourceNotFoundException(userID, User.class));
        MovieReview review = user.getMovieReviewOptional(reviewID)
                .orElseThrow(() -> new ResourceNotFoundException(reviewID, Review.class));
        MovieReview updated = reviewMapper.reviewDTOToReview(reviewDTO);
        Movie movie = review.getMovie();
        movie.getReviews().remove(review);
        user.getMovieReviews().remove(review);
        updated.setId(reviewID);
        movie.getReviews().add(updated);
        user.getMovieReviews().add(updated);
        reviewRepository.deleteById(reviewID);
        userRepository.save(user);
        movieRepository.save(movie);
        reviewRepository.save(updated);
        return reviewMapper.reviewToReviewDTO(updated);
    }


    /**
     * User(Only it's own account), Admin, Moderator
     * @param id of the User to delete
     * @throws ResourceNotFoundException if not found
     */
    @Override
    public void deleteUser(Long id) {
        userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(id, User.class));
        userRepository.deleteById(id);
    }


    /**
     * @param userID of the User we want to delete FavouriteMovie
     * @param movieID of the Movie we want to delete from the Set
     * @throws ResourceNotFoundException if either Move or User not found
     */
    @Override
    public void deleteFavouriteMovie(Long userID, Long movieID) {
        User user = userRepository.findById(userID)
                .orElseThrow(() -> new ResourceNotFoundException(userID, User.class));
        Movie movie = user.getFavMovieOptional(movieID)
                .orElseThrow(() -> new ResourceNotFoundException(movieID, Movie.class));
        user.getFavouriteMovies().remove(movie);
        movie.getUserFavourites().remove(user);
        userRepository.save(user);
        movieRepository.save(movie);
    }


    /**
     * @param userID of the User we want to delete Watched Movie
     * @param movieID of the Movie we want to delete from the Set
     * @throws ResourceNotFoundException if either Move or User not found
     */
    @Override
    public void deleteWatchedMovie(Long userID, Long movieID) {
        User user = userRepository.findById(userID)
                .orElseThrow(() -> new ResourceNotFoundException(userID, User.class));
        Movie movie = user.getWatchedMovieOptional(movieID)
                .orElseThrow(() -> new ResourceNotFoundException(movieID, Movie.class));
        user.getFavouriteMovies().remove(movie);
        movie.getUserFavourites().remove(user);
        userRepository.save(user);
        movieRepository.save(movie);

    }


    /**
     * @param userID ID of the User
     * @param reviewID ID of the Review
     * @throws ResourceNotFoundException if User or Review not found
     */
    @Override
    public void deleteMovieReview(Long userID, Long reviewID) {
        User user = userRepository.findById(userID)
                .orElseThrow(() -> new ResourceNotFoundException(userID, User.class));
        MovieReview review = user.getMovieReviewOptional(reviewID)
                .orElseThrow(() -> new ResourceNotFoundException(reviewID, Review.class));
        user.getMovieReviews().remove(review);
        Movie movie = review.getMovie();
        movie.getReviews().remove(review);
        userRepository.save(user);
        movieRepository.save(movie);
        reviewRepository.deleteById(reviewID);
    }
}



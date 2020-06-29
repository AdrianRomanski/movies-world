package adrianromanski.movies.services.user;

import adrianromanski.movies.domain.base_entity.Movie;
import adrianromanski.movies.domain.person.User;
import adrianromanski.movies.exceptions.ResourceNotFoundException;
import adrianromanski.movies.jms.JmsTextMessageService;
import adrianromanski.movies.mapper.person.UserMapper;
import adrianromanski.movies.model.person.UserDTO;
import adrianromanski.movies.repositories.MovieRepository;
import adrianromanski.movies.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.stream.Collectors.*;


@Service
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final MovieRepository movieRepository;
    private final JmsTextMessageService jmsTextMessageService;
    private final UserMapper userMapper;


    public UserServiceImpl(UserRepository userRepository, MovieRepository movieRepository,
                           JmsTextMessageService jmsTextMessageService, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.movieRepository = movieRepository;
        this.jmsTextMessageService = jmsTextMessageService;
        this.userMapper = userMapper;
    }

    /**
     * User, Admin, Moderator
     * @return List with all Users
     */
    @Override
    public List<UserDTO> getAllUsers() {
        jmsTextMessageService.sendTextMessage("Listing all Users");
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
    public UserDTO createUser(UserDTO userDTO) {
        User user = userMapper.userDTOToUser(userDTO);
        userRepository.save(user);
        jmsTextMessageService.sendTextMessage("User with id: " + user.getId() + " successfully saved");
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
        jmsTextMessageService.sendTextMessage("Adding movie with id: " + movieID + " to User with id: " + userID);
        User user = userRepository.findById(userID)
                .orElseThrow(() -> new ResourceNotFoundException(userID, User.class));
        Movie movie = movieRepository.findById(movieID)
                .orElseThrow(() -> new ResourceNotFoundException(movieID, Movie.class));
        user.getFavouriteMovies().add(movie);
        movie.getUserFavourites().add(user);
        userRepository.save(user);
        movieRepository.save(movie);
        jmsTextMessageService.sendTextMessage("Movie with id: " + movieID + " successfully added to User with id: " + userID);
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
        jmsTextMessageService.sendTextMessage("Adding movie with id: " + movieID + " to User with id: " + userID);
        User user = userRepository.findById(userID)
                .orElseThrow(() -> new ResourceNotFoundException(userID, User.class));
        Movie movie = movieRepository.findById(movieID)
                .orElseThrow(() ->  new ResourceNotFoundException(movieID, Movie.class));
        user.getWatchedMovies().add(movie);
        movie.getUserWatched().add(user);
        userRepository.save(user);
        movieRepository.save(movie);
        jmsTextMessageService.sendTextMessage("Movie with id: " + movieID + " successfully added to User with id: " + userID);
        return userMapper.userToUserDTO(user);
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
        jmsTextMessageService.sendTextMessage("Searching User with id:  " + id);
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(id, User.class));
        User updatedUser = userMapper.userDTOToUser(userDTO);
        updatedUser.setId(id);
        user.getFavouriteMovies()
                .forEach(m -> updatedUser.getFavouriteMovies().add(m));
        userRepository.save(updatedUser);
        jmsTextMessageService.sendTextMessage("User with id:  " + id + " successfully updated");
        return userMapper.userToUserDTO(updatedUser);
    }


    /**
     * User(Only it's own account), Admin, Moderator
     * @param id of the User to delete
     * @throws ResourceNotFoundException if not found
     */
    @Override
    public void deleteUser(Long id) {
        jmsTextMessageService.sendTextMessage("Searching User with id:  " + id);
        userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(id, User.class));
        userRepository.deleteById(id);
        jmsTextMessageService.sendTextMessage("User with id:  " + id + " successfully deleted");
    }


    /**
     * @param userID of the User we want to delete FavouriteMovie
     * @param movieID of the Movie we want to delete from the Set
     * @throws ResourceNotFoundException if either Move or User not found
     */
    @Override
    public void deleteFavouriteMovie(Long userID, Long movieID) {
        jmsTextMessageService.sendTextMessage("Deleting Favourite Movie with id: " + movieID + " from User with id: " + userID);
        User user = userRepository.findById(userID)
                .orElseThrow(() -> new ResourceNotFoundException(userID, User.class));
        Movie movie = user.getFavMovieOptional(movieID)
                .orElseThrow(() -> new ResourceNotFoundException(movieID, Movie.class));
        user.getFavouriteMovies().remove(movie);
        movie.getUserFavourites().remove(user);
        userRepository.save(user);
        movieRepository.save(movie);
        jmsTextMessageService.sendTextMessage("Movie with id: " + movieID + " successfully deleted from the User with id: " + userID);
    }


    /**
     * @param userID of the User we want to delete Watched Movie
     * @param movieID of the Movie we want to delete from the Set
     * @throws ResourceNotFoundException if either Move or User not found
     */
    @Override
    public void deleteWatchedMovie(Long userID, Long movieID) {
        jmsTextMessageService.sendTextMessage("Deleting Watched Movie with id: " + movieID + " from User with id: " + userID);
        User user = userRepository.findById(userID)
                .orElseThrow(() -> new ResourceNotFoundException(userID, User.class));
        Movie movie = user.getWatchedMovieOptional(movieID)
                .orElseThrow(() -> new ResourceNotFoundException(movieID, Movie.class));
        user.getFavouriteMovies().remove(movie);
        movie.getUserFavourites().remove(user);
        userRepository.save(user);
        movieRepository.save(movie);
        jmsTextMessageService.sendTextMessage("Movie with id: " + movieID + " successfully deleted from the User with id: " + userID);

    }


}

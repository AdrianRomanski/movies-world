package adrianromanski.movies.services.person;

import adrianromanski.movies.domain.base_entity.Movie;
import adrianromanski.movies.domain.person.User;
import adrianromanski.movies.exceptions.ResourceNotFoundException;
import adrianromanski.movies.jms.JmsTextMessageService;
import adrianromanski.movies.mapper.person.UserMapper;
import adrianromanski.movies.mapper.person.UserMapperImpl;
import adrianromanski.movies.model.person.UserDTO;
import adrianromanski.movies.repositories.base_entity.MovieRepository;
import adrianromanski.movies.repositories.person.UserRepository;
import adrianromanski.movies.services.user.UserService;
import adrianromanski.movies.services.user.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class UserServiceImplTest {

    @Mock
    UserRepository userRepository;

    @Mock
    MovieRepository movieRepository;

    @Mock
    JmsTextMessageService jms;

    UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        UserMapper userMapper = new UserMapperImpl();
        userService = new UserServiceImpl(userRepository, movieRepository, jms, userMapper);
    }

    private User getUser() { return User.builder().firstName("Adrian").lastName("Romanski").build(); }
    private Movie getMovie() { return Movie.builder().name("Star Wars").build(); }


    @DisplayName("Happy Path, method = getAllUsers")
    @Test
    void getAllUsers() {
        List<User> users = Arrays.asList(new User(), new User(), new User());

        when(userRepository.findAll()).thenReturn(users);

        List<UserDTO> returnDTO = userService.getAllUsers();

        assertEquals(returnDTO.size(), 3);
    }


    @DisplayName("Happy Path, method = createUser")
    @Test
    void createUser() {
        UserDTO userDTO = new UserDTO();

        userService.createUser(userDTO);

        verify(userRepository, times(1)).save(any(User.class));
    }


    @DisplayName("Happy Path, method = addFavouriteMovie")
    @Test
    void addFavouriteMovie() {
        User user = getUser();
        Movie movie = getMovie();

        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        when(movieRepository.findById(anyLong())).thenReturn(Optional.of(movie));

        UserDTO returnDTO = userService.addFavouriteMovie(1L, 1L);

        verify(userRepository, times(1)).save(any(User.class));
        verify(movieRepository, times(1)).save(any(Movie.class));

        assertEquals(returnDTO.getFavouriteMoviesDTO().size(), 1);
    }


    @DisplayName("UnHappy Path, method = addFavouriteMovie, reason = user not found")
    @Test
    void addFavouriteMovieUnHappyPathUserNotFound() {
        Throwable ex =  catchThrowable(() -> userService.addFavouriteMovie(1L, 1L));

        assertThat(ex).isInstanceOf(ResourceNotFoundException.class);
    }


    @DisplayName("UnHappy Path, method = addFavouriteMovie, reason = movie not found")
    @Test
    void addFavouriteMovieUnHappyPathMovieNotFound() {
        User user = getUser();

        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));

        Throwable ex =  catchThrowable(() -> userService.addFavouriteMovie(1L, 1L));

        assertThat(ex).isInstanceOf(ResourceNotFoundException.class);
    }


    @DisplayName("Happy Path, method = addWatched")
    @Test
    void addWatchedMovieHappyPath() {
        User user = getUser();
        Movie movie = getMovie();

        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        when(movieRepository.findById(anyLong())).thenReturn(Optional.of(movie));

        UserDTO returnDTO = userService.addWatchedMovie(1L, 1L);

        verify(userRepository, times(1)).save(any(User.class));
        verify(movieRepository, times(1)).save(any(Movie.class));

        assertEquals(returnDTO.getWatchedMoviesDTO().size(), 1);
    }


    @DisplayName("UnHappy Path, method = addWatchedMovie, reason = user not found")
    @Test
    void addWatchedMovieUnHappyPathUserNotFound() {
        Throwable ex =  catchThrowable(() -> userService.addWatchedMovie(1L, 1L));

        assertThat(ex).isInstanceOf(ResourceNotFoundException.class);
    }


    @DisplayName("UnHappy Path, method = addWatchedMovie, reason = movie not found")
    @Test
    void addWatchedMovieUnHappyPathMovieNotFound() {
        User user = getUser();

        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));

        Throwable ex =  catchThrowable(() -> userService.addWatchedMovie(1L, 1L));

        assertThat(ex).isInstanceOf(ResourceNotFoundException.class);
    }





    @DisplayName("Happy Path, method = updateUser")
    @Test
    void updateUserHappyPath() {
        UserDTO userDTO = new UserDTO();
        User user = new User();
        userDTO.setFirstName("Adrian");

        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));

        UserDTO returnDTO = userService.updateUser(1L, userDTO);

        assertEquals(returnDTO.getFirstName(), "Adrian");
    }


    @DisplayName("UnHappy Path, method = updateUser")
    @Test
    void updateUserUnHappyPath() {
        UserDTO userDTO = new UserDTO();

        Throwable ex =  catchThrowable(() -> userService.updateUser(1L, userDTO));

        assertThat(ex).isInstanceOf(ResourceNotFoundException.class);
    }


    @DisplayName("Happy Path, method = deleteById")
    @Test
    void deleteUserByIDHappyPath() {
        User user = new User();

        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));

        userService.deleteUser(1L);

        verify(userRepository, times(1)).deleteById(anyLong());
    }


    @DisplayName("UnHappy Path, method = deleteById")
    @Test
    void deleteUserByIDUnHappyPath() {
        Throwable ex =  catchThrowable(() -> userService.deleteUser(1L));

        assertThat(ex).isInstanceOf(ResourceNotFoundException.class);
    }


    @DisplayName("Happy Path, method = deleteFavouriteMovie")
    @Test
    void deleteFavouriteMovieHappyPath() {
        User user = getUser();
        Movie movie = getMovie();
        movie.setId(1L);
        user.getFavouriteMovies().add(movie);
        movie.getUserFavourites().add(user);

        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        when(movieRepository.findById(anyLong())).thenReturn(Optional.of(movie));

        userService.deleteFavouriteMovie(1L, 1L);
    }


    @DisplayName("UnHappy Path, method = deleteFavouriteMovie, reason = user not found")
    @Test
    void deleteFavouriteMovieUnHappyPathUserNotFound() {
        Throwable ex =  catchThrowable(() -> userService.deleteFavouriteMovie(1L, 1L));

        assertThat(ex).isInstanceOf(ResourceNotFoundException.class);
    }


    @DisplayName("UnHappy Path, method = deleteFavouriteMovie, reason = movie not found")
    @Test
    void deleteFavouriteMovieUnHappyPathMovieNotFound() {
        User user = getUser();

        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));

        Throwable ex =  catchThrowable(() -> userService.deleteFavouriteMovie(1L, 1L));

        assertThat(ex).isInstanceOf(ResourceNotFoundException.class);
    }


    @DisplayName("Happy Path, method = deleteWatchedMovie")
    @Test
    void deleteWatchedMovieHappyPath() {
        User user = getUser();
        Movie movie = getMovie();
        movie.setId(1L);
        user.getWatchedMovies().add(movie);
        movie.getUserWatched().add(user);

        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        when(movieRepository.findById(anyLong())).thenReturn(Optional.of(movie));

        userService.deleteWatchedMovie(1L, 1L);
    }


    @DisplayName("UnHappy Path, method = deleteWatchedMovie, reason = user not found")
    @Test
    void deleteWatchedMovieUnHappyPathUserNotFound() {
        Throwable ex =  catchThrowable(() -> userService.deleteWatchedMovie(1L, 1L));

        assertThat(ex).isInstanceOf(ResourceNotFoundException.class);
    }


    @DisplayName("UnHappy Path, method = deleteWatchedMovie, reason = movie not found")
    @Test
    void deleteWatchedMovieUnHappyPathMovieNotFound() {
        User user = getUser();

        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));

        Throwable ex =  catchThrowable(() -> userService.deleteWatchedMovie(1L, 1L));

        assertThat(ex).isInstanceOf(ResourceNotFoundException.class);
    }
}
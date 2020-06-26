package adrianromanski.movies.services;

import adrianromanski.movies.domain.Movie;
import adrianromanski.movies.domain.User;
import adrianromanski.movies.exceptions.ResourceNotFoundException;
import adrianromanski.movies.jms.JmsTextMessageService;
import adrianromanski.movies.jms.JmsTextMessageServiceImpl;
import adrianromanski.movies.mapper.UserMapper;
import adrianromanski.movies.mapper.UserMapperImpl;
import adrianromanski.movies.model.UserDTO;
import adrianromanski.movies.repositories.MovieRepository;
import adrianromanski.movies.repositories.UserRepository;
import adrianromanski.movies.services.user.UserService;
import adrianromanski.movies.services.user.UserServiceImpl;
import org.junit.jupiter.api.Assertions;
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

    @DisplayName("UnHappy Path, method = updateUser, reason = user not found")
    @Test
    void addFavouriteMovieUnHappyPathUserNotFound() {
        Throwable ex =  catchThrowable(() -> userService.addFavouriteMovie(1L, 1L));

        assertThat(ex).isInstanceOf(ResourceNotFoundException.class);
    }


    @DisplayName("UnHappy Path, method = updateUser, reason = movie not found")
    @Test
    void addFavouriteMovieUnHappyPathMovieNotFound() {
        User user = getUser();

        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));

        Throwable ex =  catchThrowable(() -> userService.addFavouriteMovie(1L, 1L));

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
        movie.getUsers().add(user);

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
}
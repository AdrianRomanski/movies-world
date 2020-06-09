package adrianromanski.movies.services;

import adrianromanski.movies.domain.User;
import adrianromanski.movies.exceptions.ResourceNotFoundException;
import adrianromanski.movies.jms.JmsTextMessageService;
import adrianromanski.movies.jms.JmsTextMessageServiceImpl;
import adrianromanski.movies.mapper.UserMapper;
import adrianromanski.movies.mapper.UserMapperImpl;
import adrianromanski.movies.model.UserDTO;
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
    JmsTextMessageService jms;

    UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        UserMapper userMapper = new UserMapperImpl();
        userService = new UserServiceImpl(userRepository, jms, userMapper);
    }


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
}
package adrianromanski.movies.services;

import adrianromanski.movies.domain.User;
import adrianromanski.movies.jms.JmsTextMessageService;
import adrianromanski.movies.jms.JmsTextMessageServiceImpl;
import adrianromanski.movies.mapper.UserMapper;
import adrianromanski.movies.mapper.UserMapperImpl;
import adrianromanski.movies.model.UserDTO;
import adrianromanski.movies.repositories.UserRepository;
import adrianromanski.movies.services.user.UserService;
import adrianromanski.movies.services.user.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

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

    @Test
    void save() {
        UserDTO userDTO = new UserDTO();

        userService.save(userDTO);

        verify(userRepository, times(1)).save(any(User.class));
    }
}
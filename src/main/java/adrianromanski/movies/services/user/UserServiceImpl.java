package adrianromanski.movies.services.user;

import adrianromanski.movies.domain.User;
import adrianromanski.movies.jms.JmsTextMessageService;
import adrianromanski.movies.mapper.UserMapper;
import adrianromanski.movies.model.UserDTO;
import adrianromanski.movies.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final JmsTextMessageService jmsTextMessageService;
    private final UserMapper userMapper;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, JmsTextMessageService jmsTextMessageService,
                           UserMapper userMapper) {
        this.userRepository = userRepository;
        this.jmsTextMessageService = jmsTextMessageService;
        this.userMapper = userMapper;
    }

    @Override
    public UserDTO save(UserDTO userDTO) {
        User user = userMapper.userDTOToUser(userDTO);
        userRepository.save(user);
        jmsTextMessageService.sendTextMessage("Saving User " + user.getUsername());
        return userMapper.userToUserDTO(user);
    }
}

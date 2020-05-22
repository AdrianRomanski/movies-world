package adrianromanski.movies.services;

import adrianromanski.movies.domain.User;
import adrianromanski.movies.mapper.UserMapper;
import adrianromanski.movies.model.UserDTO;
import adrianromanski.movies.repositories.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    public UserDTO save(UserDTO userDTO) {
        User user = userMapper.userDTOToUser(userDTO);
        userRepository.save(user);
        return userMapper.userToUserDTO(user);
    }
}

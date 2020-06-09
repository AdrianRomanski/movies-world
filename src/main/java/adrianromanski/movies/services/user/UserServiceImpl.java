package adrianromanski.movies.services.user;

import adrianromanski.movies.domain.User;
import adrianromanski.movies.exceptions.ResourceNotFoundException;
import adrianromanski.movies.jms.JmsTextMessageService;
import adrianromanski.movies.mapper.UserMapper;
import adrianromanski.movies.model.UserDTO;
import adrianromanski.movies.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.*;

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
     * User(Only it's own account), Admin, Moderator
     * @param id of the User to update
     * @param userDTO object for updating User Entity
     * @return User if successfully updated
     * @throws ResourceNotFoundException if not found
     */
    @Override
    public UserDTO updateUser(Long id, UserDTO userDTO) {
        jmsTextMessageService.sendTextMessage("Searching User with id:  " + id);
        userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(id, User.class));
        User updatedUser = userMapper.userDTOToUser(userDTO);
        updatedUser.setId(id);
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
}

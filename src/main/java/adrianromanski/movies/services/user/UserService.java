package adrianromanski.movies.services.user;

import adrianromanski.movies.model.UserDTO;

import java.util.List;

public interface UserService {

    // GET
    List<UserDTO> getAllUsers();

    // POST
    UserDTO createUser(UserDTO userDTO);

    // PUT
    UserDTO updateUser(Long id, UserDTO userDTO);

    //DELETE
    void deleteUser(Long id);
}

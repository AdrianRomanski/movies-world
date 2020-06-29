package adrianromanski.movies.services.user;

import adrianromanski.movies.model.person.UserDTO;

import java.util.List;

public interface UserService {

    // GET
    List<UserDTO> getAllUsers();

    // POST
    UserDTO createUser(UserDTO userDTO);

    UserDTO addFavouriteMovie(Long userID, Long movieID);

    UserDTO addWatchedMovie(Long userID, Long movieID);

    // PUT
    UserDTO updateUser(Long id, UserDTO userDTO);

    //DELETE
    void deleteUser(Long id);

    void deleteFavouriteMovie(Long userID, Long movieID);

    void deleteWatchedMovie(Long userID, Long movieID);

}

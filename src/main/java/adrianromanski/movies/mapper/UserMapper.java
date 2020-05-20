package adrianromanski.movies.mapper;

import adrianromanski.movies.domain.User;
import adrianromanski.movies.model.UserDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel="spring")
public interface UserMapper {

    UserDTO userToUserDTO(User user);

    User userDTOToUser(UserDTO userDTO);
}

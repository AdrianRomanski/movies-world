package adrianromanski.movies.mapper;

import adrianromanski.movies.domain.User;
import adrianromanski.movies.model.UserDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel="spring")
public interface UserMapper {

    @Mapping(source = "favouriteMovies", target = "favouriteMoviesDTO")
    UserDTO userToUserDTO(User user);

    @Mapping(source = "favouriteMoviesDTO", target = "favouriteMovies")
    User userDTOToUser(UserDTO userDTO);
}

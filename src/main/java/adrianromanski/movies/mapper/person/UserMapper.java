package adrianromanski.movies.mapper.person;

import adrianromanski.movies.domain.person.User;
import adrianromanski.movies.model.person.UserDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel="spring")
public interface UserMapper {

    @Mapping(source = "favouriteMovies", target = "favouriteMoviesDTO")
    UserDTO userToUserDTO(User user);

    @Mapping(source = "favouriteMoviesDTO", target = "favouriteMovies")
    User userDTOToUser(UserDTO userDTO);
}

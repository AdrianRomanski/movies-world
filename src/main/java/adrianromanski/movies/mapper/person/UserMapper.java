package adrianromanski.movies.mapper.person;

import adrianromanski.movies.domain.person.User;
import adrianromanski.movies.model.person.UserDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel="spring")
public interface UserMapper {

    @Mappings({
        @Mapping(source = "favouriteMovies", target = "favouriteMoviesDTO"),
            @Mapping(source = "watchedMovies", target = "watchedMoviesDTO")
    })
    UserDTO userToUserDTO(User user);

    @Mappings({
            @Mapping(source = "favouriteMoviesDTO", target = "favouriteMovies"),
            @Mapping(source = "watchedMoviesDTO", target = "watchedMovies")
    })
    User userDTOToUser(UserDTO userDTO);
}

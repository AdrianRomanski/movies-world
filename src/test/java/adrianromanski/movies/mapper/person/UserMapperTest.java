package adrianromanski.movies.mapper.person;

import adrianromanski.movies.domain.person.User;
import adrianromanski.movies.model.person.UserDTO;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UserMapperTest {

    public static final String FIRST_NAME = "Adrian";
    public static final String LAST_NAME = "Romanski";

    public static final String GENDER = "Male";
    UserMapper userMapper = new UserMapperImpl();

    @Test
    void userToUserDTO() {
        User user = User.builder().firstName(FIRST_NAME).lastName(LAST_NAME).gender(GENDER).build();
        user.setId(1L);

        UserDTO userDTO = userMapper.userToUserDTO(user);

        assertEquals(userDTO.getFirstName(), FIRST_NAME);
        assertEquals(userDTO.getLastName(), LAST_NAME);
        assertEquals(userDTO.getGender(), GENDER);
    }

    @Test
    void userDTOToUser() {
        UserDTO userDTO = UserDTO.builder().firstName(FIRST_NAME).lastName(LAST_NAME).gender(GENDER).build();

        User user = userMapper.userDTOToUser(userDTO);

        assertEquals(user.getFirstName(), FIRST_NAME);
        assertEquals(user.getLastName(), LAST_NAME);
        assertEquals(user.getGender(), GENDER);
    }
}
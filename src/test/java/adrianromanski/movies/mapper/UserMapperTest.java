package adrianromanski.movies.mapper;

import adrianromanski.movies.domain.User;
import adrianromanski.movies.model.UserDTO;
import org.junit.jupiter.api.Test;
import sun.nio.cs.US_ASCII;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class UserMapperTest {

    public static final String FIRST_NAME = "Adrian";
    public static final String LAST_NAME = "Romanski";
    public static final LocalDate DATE_OF_BIRTH = LocalDate.of(1992, 11, 3);
    public static final String GENDER = "Men";
    public static final long ID = 1L;
    UserMapper userMapper = new UserMapperImpl();

    @Test
    void userToUserDTO() {
        User user = User.builder().firstName(FIRST_NAME).lastName(LAST_NAME).dateOfBirth(DATE_OF_BIRTH).gender(GENDER).build();

        UserDTO userDTO = userMapper.userToUserDTO(user);

        assertEquals(userDTO.getFirstName(), FIRST_NAME);
        assertEquals(userDTO.getLastName(), LAST_NAME);
        assertEquals(userDTO.getDateOfBirth(), DATE_OF_BIRTH);
        assertEquals(userDTO.getGender(), GENDER);
    }

    @Test
    void userDTOToUser() {
        UserDTO userDTO = UserDTO.builder().firstName(FIRST_NAME).lastName(LAST_NAME).dateOfBirth(DATE_OF_BIRTH).gender(GENDER).build();

        User user = userMapper.userDTOToUser(userDTO);

        assertEquals(user.getFirstName(), FIRST_NAME);
        assertEquals(user.getLastName(), LAST_NAME);
        assertEquals(user.getDateOfBirth(), DATE_OF_BIRTH);
        assertEquals(user.getGender(), GENDER);
    }
}
package adrianromanski.movies.mapper.person;

import adrianromanski.movies.domain.base_entity.Movie;
import adrianromanski.movies.domain.person.Director;
import adrianromanski.movies.model.base_entity.MovieDTO;
import adrianromanski.movies.model.person.DirectorDTO;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DirectorMapperTest {


    public static final String FIRST_NAME = "Steven";
    public static final String LAST_NAME = "Spielberg";
    public static final String GENDER = "Male";
    public static final String STAR_WARS = "Star Wars";
    public static final String INDIANA_JONES = "Indiana Jones";

    DirectorMapper directorMapper = new DirectorMapperImpl();


    @Test
    void directorToDirectorDTO() {
        Movie starWars = Movie.builder().name(STAR_WARS).build();
        Movie indianaJones = Movie.builder().name(INDIANA_JONES).build();
        Director steven = Director.builder().firstName(FIRST_NAME).lastName(LAST_NAME).gender(GENDER)
                .id(1L).movies(Arrays.asList(starWars, indianaJones)).build();

        DirectorDTO stevenDTO = directorMapper.directorToDirectorDTO(steven);

        assertEquals(stevenDTO.getFirstName(), FIRST_NAME);
        assertEquals(stevenDTO.getLastName(), LAST_NAME);
        assertEquals(stevenDTO.getGender(), GENDER);

        assertEquals(stevenDTO.getMoviesDTO().get(0).getName(), STAR_WARS);
        assertEquals(stevenDTO.getMoviesDTO().get(1).getName(), INDIANA_JONES);
    }


    @Test
    void directorDTOToDirector() {
        MovieDTO starWars = MovieDTO.builder().name(STAR_WARS).build();
        MovieDTO indianaJones = MovieDTO.builder().name(INDIANA_JONES).build();
        DirectorDTO stevenDTO = DirectorDTO.builder().firstName(FIRST_NAME).lastName(LAST_NAME).gender(GENDER)
                .id(1L).moviesDTO(Arrays.asList(starWars, indianaJones)).build();

        Director steven = directorMapper.directorDTOToDirector(stevenDTO);

        assertEquals(steven.getFirstName(), FIRST_NAME);
        assertEquals(steven.getLastName(), LAST_NAME);
        assertEquals(steven.getGender(), GENDER);

        assertEquals(steven.getMovies().get(0).getName(), STAR_WARS);
        assertEquals(steven.getMovies().get(1).getName(), INDIANA_JONES);
    }
}
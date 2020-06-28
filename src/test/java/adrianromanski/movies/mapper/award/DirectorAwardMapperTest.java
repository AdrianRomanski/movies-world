package adrianromanski.movies.mapper.award;

import adrianromanski.movies.domain.award.DirectorAward;
import adrianromanski.movies.domain.person.Director;
import adrianromanski.movies.model.award.DirectorAwardDTO;
import adrianromanski.movies.model.person.DirectorDTO;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DirectorAwardMapperTest {

    public static final String FIRST_NAME = "Steven";
    public static final String LAST_NAME = "Spielberg";
    public static final String GENDER = "Male";
    public static final String CATEGORY = "Best Idea";
    public static final LocalDate DATE = LocalDate.now();
    public static final String COUNTRY = "Poland";

    DirectorAwardMapper mapper = new DirectorAwardMapperImpl();


    @Test
    void awardToAwardDTO() {
        Director steven = Director.builder().firstName(FIRST_NAME).lastName(LAST_NAME).gender(GENDER).build();
        DirectorAward award = DirectorAward.builder().awardCategory(CATEGORY).date(DATE).country(COUNTRY).director(steven).build();

        DirectorAwardDTO awardDTO = mapper.awardToAwardDTO(award);

        assertEquals(awardDTO.getAwardCategory(), CATEGORY);
        assertEquals(awardDTO.getDate(), DATE);
        assertEquals(awardDTO.getCountry(), COUNTRY);

        assertEquals(awardDTO.getDirectorDTO().getFirstName(), FIRST_NAME);
        assertEquals(awardDTO.getDirectorDTO().getLastName(), LAST_NAME);
        assertEquals(awardDTO.getDirectorDTO().getGender(), GENDER);
    }


    @Test
    void awardDTOToAward() {
        DirectorDTO stevenDTO = DirectorDTO.builder().firstName(FIRST_NAME).lastName(LAST_NAME).gender(GENDER).build();
        DirectorAwardDTO awardDTO = DirectorAwardDTO.builder().awardCategory(CATEGORY).date(DATE).country(COUNTRY).directorDTO(stevenDTO).build();

        DirectorAward award = mapper.awardDTOToAward(awardDTO);

        assertEquals(award.getAwardCategory(), CATEGORY);
        assertEquals(award.getDate(), DATE);
        assertEquals(award.getCountry(), COUNTRY);

        assertEquals(award.getDirector().getFirstName(), FIRST_NAME);
        assertEquals(award.getDirector().getLastName(), LAST_NAME);
        assertEquals(award.getDirector().getGender(), GENDER);
    }
}
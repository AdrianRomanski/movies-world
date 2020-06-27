package adrianromanski.movies.mapper.award;

import adrianromanski.movies.domain.award.MovieAward;
import adrianromanski.movies.model.award.MovieAwardDTO;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MovieAwardMapperTest {

    public static final String CATEGORY = "Best Actor";
    public static final String COUNTRY = "Poland";
    public static final LocalDate DATE = LocalDate.now();
    MovieAwardMapper mapper = new MovieAwardMapperImpl();

    @Test
    void awardToAwardDTO() {
        MovieAward award = MovieAward.builder().awardCategory(CATEGORY).country(COUNTRY).date(DATE).build();

        MovieAwardDTO awardDTO = mapper.awardToAwardDTO(award);

        assertEquals(awardDTO.getAwardCategory(), CATEGORY);
        assertEquals(awardDTO.getCountry(), COUNTRY);
        assertEquals(awardDTO.getDate(), DATE);
    }

    @Test
    void awardDTOToAward() {
        MovieAwardDTO awardDTO = MovieAwardDTO.builder().awardCategory(CATEGORY).country(COUNTRY).date(DATE).build();

        MovieAward award = mapper.awardDTOToAward(awardDTO);

        assertEquals(award.getAwardCategory(), CATEGORY);
        assertEquals(award.getCountry(), COUNTRY);
        assertEquals(award.getDate(), DATE);
    }
}
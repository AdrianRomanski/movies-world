package adrianromanski.movies.mapper.award;

import adrianromanski.movies.domain.award.ActorAward;
import adrianromanski.movies.model.award.ActorAwardDTO;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ActorAwardMapperTest {

    public static final String CATEGORY = "Best Actor";
    public static final String COUNTRY = "Poland";
    public static final LocalDate DATE = LocalDate.now();
    ActorAwardMapper mapper = new ActorAwardMapperImpl();

    @Test
    void awardToAwardDTO() {
        ActorAward award = ActorAward.builder().awardCategory(CATEGORY).country(COUNTRY).date(DATE).build();

        ActorAwardDTO awardDTO = mapper.awardToAwardDTO(award);

        assertEquals(awardDTO.getAwardCategory(), CATEGORY);
        assertEquals(awardDTO.getCountry(), COUNTRY);
        assertEquals(awardDTO.getDate(), DATE);
    }

    @Test
    void awardDTOToAward() {
        ActorAwardDTO awardDTO = ActorAwardDTO.builder().awardCategory(CATEGORY).country(COUNTRY).date(DATE).build();

        ActorAward award = mapper.awardDTOToAward(awardDTO);

        assertEquals(award.getAwardCategory(), CATEGORY);
        assertEquals(award.getCountry(), COUNTRY);
        assertEquals(award.getDate(), DATE);
    }
}
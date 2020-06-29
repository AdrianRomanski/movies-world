package adrianromanski.movies.mapper.base_entity;

import adrianromanski.movies.domain.base_entity.Episode;
import adrianromanski.movies.model.base_entity.EpisodeDTO;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EpisodeMapperTest {

    public static final String DESCRIPTION = "Final Chapter of the sage";
    public static final String URL = "img.jpg";
    public static final String NAME = "Battle of titans";
    public static final long ID = 1L;

    EpisodeMapper mapper = new EpisodeMapperImpl();

    @Test
    void episodeToEpisodeDTO() {
        Episode episode = Episode.builder().description(DESCRIPTION).imageURL(URL).name(NAME).id(ID).build();

        EpisodeDTO episodeDTO = mapper.episodeToEpisodeDTO(episode);

        assertEquals(episodeDTO.getDescription(), DESCRIPTION);
        assertEquals(episodeDTO.getImageURL(), URL);
        assertEquals(episodeDTO.getName(), NAME);
        assertEquals(episodeDTO.getId(), ID);
    }

    @Test
    void episodeDTOToEpisode() {
        EpisodeDTO episodeDTO = EpisodeDTO.builder().description(DESCRIPTION).imageURL(URL).name(NAME).id(ID).build();

        Episode episode = mapper.episodeDTOToEpisode(episodeDTO);

        assertEquals(episode.getDescription(), DESCRIPTION);
        assertEquals(episode.getImageURL(), URL);
        assertEquals(episode.getName(), NAME);
        assertEquals(episode.getId(), ID);
    }
}
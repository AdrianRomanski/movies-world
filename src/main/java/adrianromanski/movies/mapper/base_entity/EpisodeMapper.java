package adrianromanski.movies.mapper.base_entity;

import adrianromanski.movies.domain.base_entity.Episode;
import adrianromanski.movies.model.base_entity.EpisodeDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel="spring")
public interface EpisodeMapper {

    EpisodeDTO episodeToEpisodeDTO(Episode episode);

    Episode episodeDTOToEpisode(EpisodeDTO episodeDTO);
}

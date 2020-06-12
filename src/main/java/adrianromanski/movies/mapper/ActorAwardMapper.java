package adrianromanski.movies.mapper;

import adrianromanski.movies.domain.award.ActorAward;
import adrianromanski.movies.model.award.ActorAwardDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel="spring")
public interface ActorAwardMapper {

    @Mapping(source = "actor", target = "actorDTO")
    ActorAwardDTO awardToAwardDTO(ActorAward award);

    @Mapping( source = "actorDTO", target = "actor")
    ActorAward awardDTOToAward(ActorAwardDTO awardDTO);
}

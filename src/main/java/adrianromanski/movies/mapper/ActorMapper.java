package adrianromanski.movies.mapper;

import adrianromanski.movies.domain.Actor;
import adrianromanski.movies.model.ActorDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel="spring")
public interface ActorMapper {

    @Mappings({
        @Mapping(source = "awards", target = "awardsDTO"),
        @Mapping(source = "movies", target = "moviesDTO")
    })
    ActorDTO actorToActorDTO(Actor actor);

    @Mappings({
        @Mapping(source = "awardsDTO", target = "awards"),
        @Mapping(source = "moviesDTO", target = "movies")
    })
    Actor actorDTOToActor(ActorDTO actorDTO);
}

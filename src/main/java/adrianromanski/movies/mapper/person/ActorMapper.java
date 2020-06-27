package adrianromanski.movies.mapper.person;

import adrianromanski.movies.domain.person.Actor;
import adrianromanski.movies.model.person.ActorDTO;
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

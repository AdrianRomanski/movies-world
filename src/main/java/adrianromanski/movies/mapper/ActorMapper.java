package adrianromanski.movies.mapper;

import adrianromanski.movies.domain.Actor;
import adrianromanski.movies.model.ActorDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel="spring")
public interface ActorMapper {

    @Mapping(source = "movies", target = "moviesDTO")
    ActorDTO actorToActorDTO(Actor actor);

    @Mapping(source = "moviesDTO", target = "movies")
    Actor actorDTOToActor(ActorDTO actorDTO);
}

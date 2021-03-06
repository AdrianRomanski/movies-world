package adrianromanski.movies.mapper.person;

import adrianromanski.movies.domain.person.Director;
import adrianromanski.movies.model.person.DirectorDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel="spring")
public interface DirectorMapper {

    @Mappings({
            @Mapping(source = "movies", target = "moviesDTO"),
            @Mapping(source = "awards", target = "awardsDTO")
    })
    DirectorDTO directorToDirectorDTO(Director director);


    @Mappings({
            @Mapping(source = "moviesDTO", target = "movies"),
            @Mapping(source = "awardsDTO", target = "awards")
    })
    Director directorDTOToDirector(DirectorDTO directorDTO);

}

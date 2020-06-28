package adrianromanski.movies.mapper.award;

import adrianromanski.movies.domain.award.DirectorAward;
import adrianromanski.movies.model.award.DirectorAwardDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel="spring")
public interface DirectorAwardMapper {


    @Mappings({
            @Mapping(source = "director", target = "directorDTO"),
    })
    DirectorAwardDTO awardToAwardDTO(DirectorAward directorAward);

    @Mappings({
            @Mapping(source = "directorDTO", target = "director"),
    })
    DirectorAward awardDTOToAward(DirectorAwardDTO directorAwardDTO);
}

package adrianromanski.movies.mapper.award;

import adrianromanski.movies.domain.award.MovieAward;
import adrianromanski.movies.model.award.MovieAwardDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel="spring")
public interface MovieAwardMapper {

    @Mapping(source = "movie", target = "movieDTO")
    MovieAwardDTO awardToAwardDTO(MovieAward award);

    @Mapping( source = "movieDTO", target = "movie")
    MovieAward awardDTOToAward(MovieAwardDTO awardDTO);
}

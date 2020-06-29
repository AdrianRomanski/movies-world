package adrianromanski.movies.model.base_entity;

import adrianromanski.movies.model.person.DirectorDTO;
import adrianromanski.movies.model.person.UserDTO;
import adrianromanski.movies.model.award.MovieAwardDTO;
import adrianromanski.movies.model.person.ActorDTO;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@NoArgsConstructor
@Setter
public class MovieDTO extends BaseEntityDTO{

    private Long minutes;

    @Builder
    public MovieDTO(Long id,  String name,  String description, String imageURL, Long minutes,
                    CategoryDTO categoryDTO, DirectorDTO directorDTO,
                    List<ActorDTO> actorsDTO, List<MovieAwardDTO> awardsDTO,
                    Set<UserDTO> usersDTO, Set<UserDTO> usersWatchedDTO) {
        super(id, name, description, imageURL);
        this.minutes = minutes;
        this.categoryDTO = categoryDTO;
        this.directorDTO = directorDTO;
        if(actorsDTO == null){ this.actorsDTO = new ArrayList<>();}
        else{ this.actorsDTO = actorsDTO;}
        if(awardsDTO == null){ this.awardsDTO = new ArrayList<>();}
        else{ this.awardsDTO = awardsDTO;}
        if(usersDTO == null){ this.userFavouritesDTO = new HashSet<>();}
        else{ this.userFavouritesDTO = usersDTO;}
        if(usersDTO == null){ this.userWatchedDTO = new HashSet<>();}
        else{ this.userWatchedDTO = usersDTO;}
    }

    private CategoryDTO categoryDTO;
    private DirectorDTO directorDTO;
    private List<ActorDTO> actorsDTO = new ArrayList<>();
    private List<MovieAwardDTO> awardsDTO = new ArrayList<>();
    private Set<UserDTO> userFavouritesDTO = new HashSet<>();
    private Set<UserDTO> userWatchedDTO = new HashSet<>();
}

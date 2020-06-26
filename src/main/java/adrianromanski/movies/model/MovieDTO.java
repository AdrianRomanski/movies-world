package adrianromanski.movies.model;

import adrianromanski.movies.model.award.MovieAwardDTO;
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
                    CategoryDTO categoryDTO, List<ActorDTO> actorsDTO, List<MovieAwardDTO> awardsDTO,
                    Set<UserDTO> usersDTO) {
        super(id, name, description, imageURL);
        this.minutes = minutes;
        this.categoryDTO = categoryDTO;
        if(actorsDTO == null){this.actorsDTO = new ArrayList<>();}
        this.actorsDTO = actorsDTO;
        if(awardsDTO == null){this.awardsDTO = new ArrayList<>();}
        this.awardsDTO = awardsDTO;
        if(usersDTO == null){this.usersDTO = new HashSet<>();}
        this.usersDTO = usersDTO;
    }

    private CategoryDTO categoryDTO;
    private List<ActorDTO> actorsDTO = new ArrayList<>();
    private List<MovieAwardDTO> awardsDTO = new ArrayList<>();
    private Set<UserDTO> usersDTO = new HashSet<>();
}

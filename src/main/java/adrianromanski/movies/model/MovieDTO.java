package adrianromanski.movies.model;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@Setter
public class MovieDTO extends BaseEntityDTO{

    private Long minutes;

    @Builder
    public MovieDTO(Long id,  String name,  String description, String imageURL, Long minutes,
                    CategoryDTO categoryDTO, List<ActorDTO> actorsDTO) {
        super(id, name, description, imageURL);
        this.minutes = minutes;
        this.categoryDTO = categoryDTO;
        this.actorsDTO = actorsDTO;
    }

    private CategoryDTO categoryDTO;
    private List<ActorDTO> actorsDTO = new ArrayList<>();
}

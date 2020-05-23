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

    @Builder
    public MovieDTO(Long id,  String name,  String description, String imageURL,
                    CategoryDTO categoryDTO, List<ActorDTO> actorsDTO) {
        super(id, name, description, imageURL);
        this.categoryDTO = categoryDTO;
        this.actorsDTO = actorsDTO;
    }

    private CategoryDTO categoryDTO;
    private List<ActorDTO> actorsDTO = new ArrayList<>();
}

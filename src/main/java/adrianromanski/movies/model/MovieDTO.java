package adrianromanski.movies.model;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@Setter
public class MovieDTO extends BaseEntityDTO{

    @Builder
    public MovieDTO(Long id,  String name,  String description, String imageURL, CategoryDTO categoryDTO) {
        super(id, name, description, imageURL);
        this.categoryDTO = categoryDTO;
    }

    private CategoryDTO categoryDTO;
}

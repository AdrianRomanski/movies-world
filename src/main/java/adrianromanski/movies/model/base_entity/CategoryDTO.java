package adrianromanski.movies.model.base_entity;

import lombok.*;

import java.util.ArrayList;
import java.util.List;


@Getter
@NoArgsConstructor
@Setter
public class CategoryDTO extends BaseEntityDTO{

    @Builder
    public CategoryDTO(Long id,String name, String description, String imageURL, List<MovieDTO> moviesDTO) {
        super(id, name, description, imageURL);
        if(moviesDTO == null){ this.moviesDTO = new ArrayList<>();}
        else{ this.moviesDTO = moviesDTO;}
    }

    private List<MovieDTO> moviesDTO = new ArrayList<>();
}

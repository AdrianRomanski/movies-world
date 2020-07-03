package adrianromanski.movies.model.base_entity;

import com.google.common.collect.ImmutableList;
import lombok.*;

import java.util.ArrayList;
import java.util.List;


@Getter
@NoArgsConstructor
@Setter
public class CategoryDTO extends BaseEntityDTO{

    /**
     * I have to use this kind of structure because otherwise i couldn't initialize mutable Collection
     * with @Singular annotation because it uses ImmutableList by default
     * @see Singular
     * @see ImmutableList
     */
    @Builder
    public CategoryDTO(Long id,String name, String description, String imageURL, List<MovieDTO> moviesDTO) {
        super(id, name, description, imageURL);
        if(moviesDTO == null){ this.moviesDTO = new ArrayList<>();}
        else{ this.moviesDTO = moviesDTO;}
    }

    private List<MovieDTO> moviesDTO = new ArrayList<>();
}

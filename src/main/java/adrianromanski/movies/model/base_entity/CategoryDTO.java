package adrianromanski.movies.model.base_entity;

import com.google.common.collect.ImmutableList;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


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
    public CategoryDTO(Long id,String name, String description, Byte[] image, List<MovieDTO> moviesDTO) {
        super(id, name, description, image);
        this.moviesDTO = Objects.requireNonNullElseGet(moviesDTO, ArrayList::new);
    }

    private List<MovieDTO> moviesDTO = new ArrayList<>();
}

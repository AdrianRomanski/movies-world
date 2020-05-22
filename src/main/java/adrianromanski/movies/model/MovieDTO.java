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
    public MovieDTO(Long id,  String name,  String description, String imageURL) {
        super(id, name, description, imageURL);
    }
}

package adrianromanski.movies.model;

import lombok.*;


@Getter
@NoArgsConstructor
@Setter
public class CategoryDTO extends BaseEntityDTO{

    @Builder
    public CategoryDTO(Long id,String name, String description, String imageURL) {
        super(id, name, description, imageURL);
    }
}

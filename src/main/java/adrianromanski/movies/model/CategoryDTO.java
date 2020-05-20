package adrianromanski.movies.model;

import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
public class CategoryDTO {

    private Long id;
    private String name;
    private String description;

    private String imageURl;

    @Builder
    public CategoryDTO(String name, String description, String imageURl) {
        this.name = name;
        this.description = description;
        this.imageURl = imageURl;
    }
}

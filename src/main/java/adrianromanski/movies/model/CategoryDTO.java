package adrianromanski.movies.model;

import lombok.*;

@Getter
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

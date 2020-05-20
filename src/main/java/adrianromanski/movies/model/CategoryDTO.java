package adrianromanski.movies.model;

import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
public class CategoryDTO {

    private Long id;

    @NotNull
    @Size(min = 1, max = 25)
    private String name;

    @NotNull
    @Size(min = 10, max = 50)
    private String description;

    @Builder
    public CategoryDTO(String name, String description) {
        this.name = name;
        this.description = description;
    }
}

package adrianromanski.movies.domain;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@Entity
@NoArgsConstructor
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    @Size(min = 1, max = 25)
    private String name;
    @NotNull
    @Size(min = 160, max = 200)
    private String description;
    private String imageURl;

    @Builder
    public Category(String name, String description, String imageURl) {
        this.name = name;
        this.description = description;
        this.imageURl = imageURl;
    }
}
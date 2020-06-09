package adrianromanski.movies.domain;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@Entity
@NoArgsConstructor
public class Category extends BaseEntity {


    @Builder
    public Category(Long id, String name, String description, String imageURL, List<Movie> movies) {
        super(id, name, description, imageURL);
        this.movies = movies;
    }

    @OneToMany(mappedBy = "category")
    private List<Movie> movies = new ArrayList<>();
}
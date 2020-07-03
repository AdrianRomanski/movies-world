package adrianromanski.movies.domain.base_entity;

import com.google.common.collect.ImmutableList;
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


    /**
     * I have to use this kind of structure because otherwise i couldn't initialize mutable Collection
     * with @Singular annotation because it uses ImmutableList by default
     * @see Singular
     * @see ImmutableList
     */
    @Builder
    public Category(Long id, String name, String description, String imageURL, List<Movie> movies) {
        super(id, name, description, imageURL);
        if(movies == null){ this.movies = new ArrayList<>();}
        else{ this.movies = movies;}
    }

    @OneToMany(mappedBy = "category")
    private List<Movie> movies = new ArrayList<>();
}
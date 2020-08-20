package adrianromanski.movies.domain.base_entity;

import com.google.common.collect.ImmutableList;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Setter
@Getter
@Entity
@NoArgsConstructor
public class Category extends BaseEntity {

    @Lob
    private Byte[] image;

    /**
     * I have to use this kind of structure because otherwise i couldn't initialize mutable Collection
     * with @Singular annotation because it uses ImmutableList by default
     * @see Singular
     * @see ImmutableList
     */
    @Builder
    public Category(Long id, String name, String description, String imageURL, Byte[] image, List<Movie> movies) {
        super(id, name, description, imageURL);
        this.image = image;
        this.movies = Objects.requireNonNullElseGet(movies, ArrayList::new);
    }

    @OneToMany(mappedBy = "category", cascade=CascadeType.ALL)
    private List<Movie> movies = new ArrayList<>();



    //, fetch = FetchType.LAZY,
    //            cascade = {
    //            CascadeType.PERSIST,
    //            CascadeType.MERGE
    //    })
}
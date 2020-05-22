package adrianromanski.movies.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import javax.persistence.Entity;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class Movie extends BaseEntity {

    @Builder
    public Movie(Long id, String name, String description, String imageURL) {
        super(id, name, description, imageURL);
    }
}

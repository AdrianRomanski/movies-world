package adrianromanski.movies.domain;

import lombok.*;

import javax.persistence.Entity;

@Setter
@Getter
@Entity
@NoArgsConstructor
public class Category extends BaseEntity {

    @Builder
    public Category(Long id, String name, String description, String imageURL) {
        super(id, name, description, imageURL);
    }
}
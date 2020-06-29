package adrianromanski.movies.domain.base_entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class Episode extends BaseEntity {

    @Builder
    public Episode(Long id, String name, String description, String imageURL) {
        super(id, name, description, imageURL);
    }
}

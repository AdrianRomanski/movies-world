package adrianromanski.movies.domain.base_entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class Episode extends BaseEntity {

    @Builder
    public Episode(Long id, String name, String description, Byte[] image) {
        super(id, name, description, image);
    }

    @ManyToOne
    private Series series;
}

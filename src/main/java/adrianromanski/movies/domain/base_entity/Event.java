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
public class Event extends BaseEntity{

    @Builder
    public Event(Long id, String name, String description, Byte[] image) {
        super(id, name, description, image);
    }
}

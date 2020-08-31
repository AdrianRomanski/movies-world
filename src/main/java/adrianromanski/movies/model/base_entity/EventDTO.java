package adrianromanski.movies.model.base_entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class EventDTO extends BaseEntityDTO{

    @Builder
    public EventDTO(Long id, String name, String description, Byte[] image) {
        super(id, name, description, image);
    }
}

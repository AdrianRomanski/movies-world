package adrianromanski.movies.model.base_entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
@NoArgsConstructor
public class EventDTO extends BaseEntityDTO{

    LocalDate date;

    @Builder
    public EventDTO(Long id, String name, String description, Byte[] image, LocalDate date) {
        super(id, name, description, image);
        this.date = date;
    }
}

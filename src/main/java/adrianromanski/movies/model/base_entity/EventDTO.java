package adrianromanski.movies.model.base_entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Max;
import java.time.LocalDate;

@Setter
@Getter
@NoArgsConstructor
public class EventDTO extends BaseEntityDTO{

    LocalDate date;

    @Max(1000)
    String largeDescription;

    @Builder
    public EventDTO(Long id, String name, String description, Byte[] image, LocalDate date, String largeDescription) {
        super(id, name, description, image);
        this.date = date;
        this.largeDescription = largeDescription;
    }
}

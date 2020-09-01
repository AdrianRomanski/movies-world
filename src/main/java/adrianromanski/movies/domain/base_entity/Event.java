package adrianromanski.movies.domain.base_entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class Event extends BaseEntity{

    LocalDate date;

    @Builder
    public Event(Long id, String name, String description, Byte[] image, LocalDate date) {
        super(id, name, description, image);
        this.date = date;
    }
}

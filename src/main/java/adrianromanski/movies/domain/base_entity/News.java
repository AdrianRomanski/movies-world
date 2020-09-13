package adrianromanski.movies.domain.base_entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class News extends BaseEntity{

    LocalDate date;

    @Column(length = 1000)
    String largeDescription;

    @Builder
    public News(Long id, String name, String description, Byte[] image, LocalDate date, String largeDescription) {
        super(id, name, description, image);
        this.date = date;
        this.largeDescription = largeDescription;
    }
}

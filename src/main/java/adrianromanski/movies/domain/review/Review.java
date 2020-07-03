package adrianromanski.movies.domain.review;

import adrianromanski.movies.domain.base_entity.BaseEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.MappedSuperclass;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@NoArgsConstructor
@MappedSuperclass
public class Review extends BaseEntity {

    private LocalDate date;
    private Integer score;

    @Builder
    public Review(Long id, String name, String description, String imageURL,
                  LocalDate date, Integer score) {
        super(id, name, description, imageURL);
        this.date = date;
        this.score = score;
    }
}

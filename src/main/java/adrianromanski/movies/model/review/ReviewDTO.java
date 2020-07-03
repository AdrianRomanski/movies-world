package adrianromanski.movies.model.review;

import adrianromanski.movies.model.base_entity.BaseEntityDTO;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.MappedSuperclass;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@MappedSuperclass
public class ReviewDTO extends BaseEntityDTO {

    private LocalDate date;
    private Integer score;

    @Builder
    public ReviewDTO(Long id, String name, String description, String imageURL,
                  LocalDate date, Integer score) {
        super(id, name, description, imageURL);
        this.date = date;
        this.score = score;
    }
}

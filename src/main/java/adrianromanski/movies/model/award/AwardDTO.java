package adrianromanski.movies.model.award;

import adrianromanski.movies.model.base_entity.BaseEntityDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.MappedSuperclass;
import java.time.LocalDate;

@MappedSuperclass
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AwardDTO extends BaseEntityDTO {

    private LocalDate date;
    private String country;
    private String awardCategory;
}

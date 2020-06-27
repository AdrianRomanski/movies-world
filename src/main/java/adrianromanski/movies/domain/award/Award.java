package adrianromanski.movies.domain.award;

import adrianromanski.movies.domain.base_entity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.MappedSuperclass;
import java.time.LocalDate;

@Getter
@Setter
@MappedSuperclass
@NoArgsConstructor
@AllArgsConstructor
public class Award extends BaseEntity {

    private LocalDate date;
    private String country;
    private String awardCategory; // That could be another entity

}

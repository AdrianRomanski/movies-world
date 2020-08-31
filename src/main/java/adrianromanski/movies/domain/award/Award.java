package adrianromanski.movies.domain.award;

import adrianromanski.movies.domain.base_entity.BaseEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.MappedSuperclass;
import java.time.LocalDate;

@Getter
@Setter
@MappedSuperclass
@NoArgsConstructor
public class Award extends BaseEntity {

    private LocalDate date;
    private String country;
    private String awardCategory; // That could be another entity or m


    public Award(Long id, String name, String description, Byte[] image,
                 LocalDate date, String country, String awardCategory) {
        super(id, name, description, image);
        this.date = date;
        this.country = country;
        this.awardCategory = awardCategory;
    }
}

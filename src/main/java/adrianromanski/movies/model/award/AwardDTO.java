package adrianromanski.movies.model.award;

import adrianromanski.movies.model.base_entity.BaseEntityDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.MappedSuperclass;
import java.time.LocalDate;

@MappedSuperclass
@Getter
@Setter
@NoArgsConstructor
public class AwardDTO extends BaseEntityDTO {

    private LocalDate date;
    private String country;
    private String awardCategory;


    public AwardDTO(Long id, String name, String description, String imageURL,
                    LocalDate date, String country, String awardCategory) {
        super(id, name, description, imageURL);
        this.date = date;
        this.country = country;
        this.awardCategory = awardCategory;
    }
}

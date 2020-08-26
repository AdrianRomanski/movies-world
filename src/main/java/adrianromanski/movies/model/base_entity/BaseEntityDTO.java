package adrianromanski.movies.model.base_entity;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@MappedSuperclass
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BaseEntityDTO {

    private Long id;

    @NotEmpty
    @Size(min = 3, max = 30)
    private String name;
    @NotEmpty
    @Size(min = 160, max = 255)
    private String description;
//    @NotEmpty
    private String imageURL;


}

package adrianromanski.movies.model.base_entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class EpisodeDTO extends BaseEntityDTO{

    @Builder
    public EpisodeDTO(Long id, String name,  String description, Byte[] image) {
        super(id, name, description, image);
    }
}

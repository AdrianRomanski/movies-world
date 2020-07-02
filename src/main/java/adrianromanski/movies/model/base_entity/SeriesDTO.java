package adrianromanski.movies.model.base_entity;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SeriesDTO extends BaseEntityDTO {


    @Builder
    public SeriesDTO(Long id, String name, String description, String imageURL,
                     List<EpisodeDTO> episodesDTO) {
        super(id, name, description, imageURL);
        if(episodesDTO == null){ this.episodesDTO = new ArrayList<>();}
        else { this.episodesDTO = episodesDTO; }

    }

    private List<EpisodeDTO> episodesDTO = new ArrayList<>();
}

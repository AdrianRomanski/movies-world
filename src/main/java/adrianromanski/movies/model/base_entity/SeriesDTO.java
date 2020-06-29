package adrianromanski.movies.model.base_entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class SeriesDTO extends BaseEntityDTO {


    @Builder
    public SeriesDTO(Long id, String name, String description, String imageURL,
                     List<EpisodeDTO> episodesDTO) {
        super(id, name, description, imageURL);
        if(episodesDTO == null){ this.episodesDTO = new ArrayList<>();}
        else { this.episodesDTO = episodesDTO; }
    }

    private List<EpisodeDTO> episodesDTO;
}

package adrianromanski.movies.model.base_entity;

import com.google.common.collect.ImmutableList;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class SeriesDTO extends BaseEntityDTO {

    /**
     * I have to use this kind of structure because otherwise i couldn't initialize mutable Collection
     * with @Singular annotation because it uses ImmutableList by default
     * @see Singular
     * @see ImmutableList
     */
    @Builder
    public SeriesDTO(Long id, String name, String description, String imageURL,
                     List<EpisodeDTO> episodesDTO) {
        super(id, name, description, imageURL);
        if(episodesDTO == null){ this.episodesDTO = new ArrayList<>();}
        else { this.episodesDTO = episodesDTO; }
    }

    private List<EpisodeDTO> episodesDTO = new ArrayList<>();
}

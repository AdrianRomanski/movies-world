package adrianromanski.movies.domain.base_entity;

import com.google.common.collect.ImmutableList;
import lombok.*;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class Series extends BaseEntity {

    /**
     * I have to use this kind of structure because otherwise i couldn't initialize mutable Collection
     * with @Singular annotation because it uses ImmutableList by default
     * @see Singular
     * @see ImmutableList
     */
    @Builder
    public Series(Long id, String name, String description, String imageURL, List<Episode> episodes) {
        super(id, name, description, imageURL);
        if(episodes == null){ this.episodes = new ArrayList<>();}
        else { this.episodes = episodes; }
    }

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "series", orphanRemoval = true)
    private List<Episode> episodes = new ArrayList<>();

    public Optional<Episode> getEpisodeOptional(Long id) {
        return this.getEpisodes()
                .stream()
                .filter(e -> e.getId().equals(id))
                .findFirst();
    }
}

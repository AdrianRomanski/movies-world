package adrianromanski.movies.domain.base_entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class Series extends BaseEntity {


    @Builder
    public Series(Long id, String name, String description, String imageURL,
                  List<Episode> episodes) {
        super(id, name, description, imageURL);
        if(episodes == null){ this.episodes = new ArrayList<>();}
        else { this.episodes = episodes; }
    }

    @OneToMany
    private List<Episode> episodes = new ArrayList<>();
}

package adrianromanski.movies.bootstrap;

import adrianromanski.movies.domain.Category;
import adrianromanski.movies.repositories.CategoryRepository;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
public class MoviesBootstrap implements ApplicationListener<ContextRefreshedEvent> {

    private final CategoryRepository categoryRepository;

    public MoviesBootstrap(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {

        Category fantasy = Category.builder().name("Fantasy").imageURL("fantasy.jpg")
                .description("Fantasy films are films that belong to the fantasy genre with fantastic themes, usually magic," +
                            "supernatural events, mythology, folklore, or exotic fantasy worlds").build();

        Category horror = Category.builder().name("Horror").imageURL("horror.jpg")
                .description("A horror film is a film that seeks to elicit fear for entertainment purposes " +
                                "Initially inspired by literature from authors such as Edgar Allan Poe, Bram Stoker, and Mary Shelley").build();

        Category sciFi = Category.builder().name("Sci-Fi").imageURL("sci-fi.jpg")
                .description("A Sci-Fi film is science-based depictions of phenomena that are not fully accepted by mainstream science, " +
                                "such as extraterrestrial lifeforms, alien worlds or time travel").build();

        categoryRepository.save(fantasy);
        categoryRepository.save(horror);
        categoryRepository.save(sciFi);



    }
}

package adrianromanski.movies.bootstrap;

import adrianromanski.movies.domain.Actor;
import adrianromanski.movies.domain.Category;
import adrianromanski.movies.domain.Movie;
import adrianromanski.movies.repositories.ActorRepository;
import adrianromanski.movies.repositories.CategoryRepository;
import adrianromanski.movies.repositories.MovieRepository;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collections;

@Component
public class MoviesBootstrap implements ApplicationListener<ContextRefreshedEvent> {

    private final CategoryRepository categoryRepository;
    private final MovieRepository movieRepository;
    private final ActorRepository actorRepository;


    public MoviesBootstrap(CategoryRepository categoryRepository, MovieRepository movieRepository, ActorRepository actorRepository) {
        this.categoryRepository = categoryRepository;
        this.movieRepository = movieRepository;
        this.actorRepository = actorRepository;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {

        Movie movie = Movie.builder().name("Lord of the Rings").description("Fantastic journey").imageURL("fantasy.jpg").build();

        Category fantasy = Category.builder().name("Fantasy").imageURL("fantasy.jpg")
                .description("Fantasy films are films that belong to the fantasy genre with fantastic themes, usually magic," +
                            "supernatural events, mythology, folklore, or exotic fantasy worlds").movies(Arrays.asList(movie, movie)).build();

        movie.setCategory(fantasy);

        Actor actor = Actor.builder().firstName("Arnold").lastName("Schwarzenegger").gender("Male").movies(Collections.singletonList(movie)).build();







        Category horror = Category.builder().name("Horror").imageURL("horror.jpg")
                .description("A horror film is a film that seeks to elicit fear for entertainment purposes " +
                                "Initially inspired by literature from authors such as Edgar Allan Poe, Bram Stoker, and Mary Shelley").build();

        Category sciFi = Category.builder().name("Sci-Fi").imageURL("sci-fi.jpg")
                .description("A Sci-Fi film is science-based depictions of phenomena that are not fully accepted by mainstream science, " +
                                "such as extraterrestrial lifeforms, alien worlds or time travel").build();


        categoryRepository.save(fantasy);
        categoryRepository.save(horror);
        categoryRepository.save(sciFi);
        movieRepository.save(movie);
        actorRepository.save(actor);



    }
}

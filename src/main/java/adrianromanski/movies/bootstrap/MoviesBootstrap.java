package adrianromanski.movies.bootstrap;

import adrianromanski.movies.domain.base_entity.Category;
import adrianromanski.movies.domain.base_entity.Movie;
import adrianromanski.movies.domain.person.Actor;
import adrianromanski.movies.repositories.base_entity.CategoryRepository;
import adrianromanski.movies.repositories.base_entity.MovieRepository;
import adrianromanski.movies.repositories.person.ActorRepository;
import lombok.SneakyThrows;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
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

    @SneakyThrows
    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {

        Movie movie = Movie.builder().name("Lord of the Rings").description("Fantastic journey").build();

        Byte[] fantasyImage = getBytes("src/main/resources/static/images/fantasy.jpg");
        Byte[] horrorImage = getBytes("src/main/resources/static/images/horror.jpg");
        Byte[] scifiImage = getBytes("src/main/resources/static/images/sci-fi.jpg");


        Category fantasy = Category.builder().name("Fantasy").image(fantasyImage)
                .description("Fantasy films are films that belong to the fantasy genre with fantastic themes, usually magic, " +
                            "supernatural events, mythology, folklore, or exotic fantasy worlds").movies(Arrays.asList(movie, movie)).build();

        movie.setCategory(fantasy);

        Actor actor = Actor.builder().firstName("Arnold").lastName("Schwarzenegger").gender("Male").movies(Collections.singletonList(movie)).build();


        Category horror = Category.builder().name("Horror").image(horrorImage)
                .description("A horror film is a film that seeks to elicit fear for entertainment purposes " +
                                "Initially inspired by literature from authors such as Edgar Allan Poe, Bram Stoker, and Mary Shelley").build();

        Category sciFi = Category.builder().name("Sci-Fi").image(scifiImage)
                .description("A Sci-Fi film is science-based depictions of phenomena that are not fully accepted by mainstream science, " +
                                "such as extraterrestrial lifeforms, alien worlds or time travel").build();


        categoryRepository.save(fantasy);
        categoryRepository.save(horror);
        categoryRepository.save(sciFi);
        movieRepository.save(movie);
        actorRepository.save(actor);



    }

    private Byte[] getBytes(String path) throws IOException {
        BufferedImage bImage = ImageIO.read(new File(path));
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ImageIO.write(bImage, "jpg", bos );
        byte [] data = bos.toByteArray();
        Byte[] bytes = new Byte[data.length];
        Arrays.setAll(bytes, n -> data[n]);
        return bytes;
    }
}

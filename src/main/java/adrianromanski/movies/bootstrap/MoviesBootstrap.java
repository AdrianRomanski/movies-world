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


        // Star Wars Images
        Byte[] starWars1Image = getBytes("src/main/resources/static/images/science-fiction/star_wars/part1.jpg");
        Byte[] starWars2Image = getBytes("src/main/resources/static/images/science-fiction/star_wars/part2.jpg");
        Byte[] starWars3Image = getBytes("src/main/resources/static/images/science-fiction/star_wars/part3.jpg");
        Byte[] starWars4Image = getBytes("src/main/resources/static/images/science-fiction/star_wars/part4.jpg");
        Byte[] starWars5Image = getBytes("src/main/resources/static/images/science-fiction/star_wars/part5.jpg");
        Byte[] starWars6Image = getBytes("src/main/resources/static/images/science-fiction/star_wars/part6.jpg");

        // Star Wars Movies
        Movie startWars1 = Movie.builder().name("Star Wars: Phantom Menace").description("Two Jedi escape a hostile blockade to find allies and come across a young boy who may bring balance to the Force, " +
                "but the long dormant Sith resurface to claim their original glory.").image(starWars1Image).build();
        Movie startWars2 = Movie.builder().name("Star Wars: Attack Of The Clones").description("Ten years after initially meeting, Anakin Skywalker shares a forbidden romance with Padmé Amidala, " +
                        "while Obi-Wan Kenobi investigates an assassination attempt on the senator and discovers a secret clone army crafted for the Jedi").image(starWars2Image).build();
        Movie startWars3 = Movie.builder().name("Star Wars: Revenge of the Sith").description("Three years into the Clone Wars, the Jedi rescue Palpatine from Count Dooku. As Obi-Wan pursues a new threat," +
                " Anakin acts as a double agent between the Jedi Council and Palpatine and is lured into a sinister plan to rule the galaxy").image(starWars3Image).build();
        Movie startWars4 = Movie.builder().name("Star Wars: A New Hope").description("Luke Skywalker joins forces with a Jedi Knight, a cocky pilot, a Wookiee and two droids to save the galaxy from the Empire's world-destroying battle station, " +
                "while also attempting to rescue Princess Leia from the mysterious Darth Vader").image(starWars4Image).build();
        Movie startWars5 = Movie.builder().name("Star Wars: The Empire Strikes Back").description("After the Rebels are brutally overpowered by the Empire on the ice planet Hoth, Luke Skywalker begins Jedi training with Yoda," +
                " while his friends are pursued by Darth Vader and a bounty hunter named Boba Fett all over the galaxy").image(starWars5Image).build();
        Movie startWars6 = Movie.builder().name("Star Wars Return of the the Jedi").description("After a daring mission to rescue Han Solo from Jabba the Hutt, the Rebels dispatch to Endor to destroy the second Death Star. " +
                "Meanwhile, Luke struggles to help Darth Vader back from the dark side without falling into the Emperor's trap.").image(starWars6Image).build();

        // Back to the Future Images
        Byte[] backToTheFuture1Image = getBytes("src/main/resources/static/images/science-fiction/back_to_the_future/part1.jpg");
        Byte[] backToTheFuture2Image = getBytes("src/main/resources/static/images/science-fiction/back_to_the_future/part2.jpg");
        Byte[] backToTheFuture3Image = getBytes("src/main/resources/static/images/science-fiction/back_to_the_future/part3.jpg");

        // Back to the future movies
        Movie backToTheFuture1 = Movie.builder().name("Back To The Future Part 1").description("Marty McFly, a 17-year-old high school student, is accidentally sent thirty years into the past in a time-traveling DeLorean " +
                "invented by his close friend, the eccentric scientist Doc Brown.").image(backToTheFuture1Image).build();
        Movie backToTheFuture2 = Movie.builder().name("Back To The Future Part 2").description("After visiting 2015, Marty McFly must repeat his visit to 1955 to prevent disastrous changes to 1985..." +
                "without interfering with his first trip").image(backToTheFuture2Image).build();
        Movie backToTheFuture3 = Movie.builder().name("Back To The Future Part 3").description("Stranded in 1955, Marty McFly learns about the death of Doc Brown in 1885 and must travel back in time to save him." +
                " With no fuel readily available for the DeLorean, the two must figure how to escape the Old West before Emmett is murdered.").image(backToTheFuture3Image).build();

        // Matrix
        Byte[] matrixPict = getBytes("src/main/resources/static/images/science-fiction/matrix.jpg");

        Movie matrix = Movie.builder().name("Matrix").description("A computer hacker learns from mysterious rebels about the true nature of his reality and his role in the war against its controllers.")
                .image(matrixPict).build();

        // The Fifth Element
        Byte[] fifthElementPict = getBytes("src/main/resources/static/images/science-fiction/fifthElement.jpg");

        Movie fifthElement = Movie.builder().name("The Fifth Element").description("In the colorful future, a cab driver unwittingly becomes the central figure in the search for a legendary cosmic weapon to keep Evil and Mr. Zorg at bay")
                .image(fifthElementPict).build();

        // Interstellar
        Byte[] interstellarPict = getBytes("src/main/resources/static/images/science-fiction/interstellar.jpg");

        Movie interstellar = Movie.builder().name("Interstellar").description("A team of explorers travel through a wormhole in space in an attempt to ensure humanity's survival.")
                .image(interstellarPict).build();


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

        Category sciFi = Category.builder().name("Sci-Fi").image(scifiImage).movies(Arrays.asList(startWars1, startWars2, startWars3))
                .description("A Sci-Fi film is science-based depictions of phenomena that are not fully accepted by mainstream science, " +
                                "such as extraterrestrial lifeforms, alien worlds or time travel").build();

        startWars1.setCategory(sciFi);
        startWars2.setCategory(sciFi);
        startWars3.setCategory(sciFi);
        startWars4.setCategory(sciFi);
        startWars5.setCategory(sciFi);
        startWars6.setCategory(sciFi);

        backToTheFuture1.setCategory(sciFi);
        backToTheFuture2.setCategory(sciFi);
        backToTheFuture3.setCategory(sciFi);

        matrix.setCategory(sciFi);
        interstellar.setCategory(sciFi);
        fifthElement.setCategory(sciFi);

        categoryRepository.save(fantasy);
        categoryRepository.save(horror);
        categoryRepository.save(sciFi);
        movieRepository.save(movie);
        movieRepository.save(startWars1);
        movieRepository.save(startWars2);
        movieRepository.save(startWars3);
        movieRepository.save(startWars4);
        movieRepository.save(startWars5);
        movieRepository.save(startWars6);
        movieRepository.save(backToTheFuture1);
        movieRepository.save(backToTheFuture2);
        movieRepository.save(backToTheFuture3);
        movieRepository.save(interstellar);
        movieRepository.save(matrix);
        movieRepository.save(fifthElement);
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

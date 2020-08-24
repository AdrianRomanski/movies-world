package adrianromanski.movies.bootstrap;

import adrianromanski.movies.domain.base_entity.Category;
import adrianromanski.movies.domain.base_entity.Movie;
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


        initScienceFiction();

        // The Lord Of The Rings
        Byte[] lordOfTheRings1Image = getBytes("src/main/resources/static/images/fantasy/lord_of_the_rings/part1.jpg");
        Byte[] lordOfTheRings2Image = getBytes("src/main/resources/static/images/fantasy/lord_of_the_rings/part2.jpg");
        Byte[] lordOfTheRings3Image = getBytes("src/main/resources/static/images/fantasy/lord_of_the_rings/part3.jpg");

        Movie lordOfTheRings1 = Movie.builder().name("The Lord of the Rings: Fellowship of the rings").image(lordOfTheRings1Image)
                .description("A meek Hobbit from the Shire and eight companions set out on a journey to destroy the powerful One Ring and save Middle-earth from the Dark Lord Sauron.").build();

        Movie lordOfTheRings2 = Movie.builder().name("The Lord of the Rings: Two Towers").image(lordOfTheRings2Image)
                .description("While Frodo and Sam edge closer to Mordor with the help of the shifty Gollum, the divided fellowship makes a stand against Sauron's new ally, Saruman, and his hordes of Isengard.").build();

        Movie lordOfTheRings3 = Movie.builder().name("The Lord of the Rings: The Return of the King").image(lordOfTheRings3Image)
                .description("Gandalf and Aragorn lead the World of Men against Sauron's army to draw his gaze from Frodo and Sam as they approach Mount Doom with the One Ring.").build();

        // Harry Potter
        Byte[] harryPotter1Image = getBytes("src/main/resources/static/images/fantasy/harryPotter/part1.jpg");
        Byte[] harryPotter2Image = getBytes("src/main/resources/static/images/fantasy/harryPotter/part2.jpg");
        Byte[] harryPotter3Image = getBytes("src/main/resources/static/images/fantasy/harryPotter/part3.jpg");
        Byte[] harryPotter4Image = getBytes("src/main/resources/static/images/fantasy/harryPotter/part4.jpg");
        Byte[] harryPotter5Image = getBytes("src/main/resources/static/images/fantasy/harryPotter/part5.jpg");
        Byte[] harryPotter6Image = getBytes("src/main/resources/static/images/fantasy/harryPotter/part6.jpg");

        Movie harryPotter1 = Movie.builder().name("Harry Potter and the Sorcerer's Stone").image(harryPotter1Image)
                .description("An orphaned boy enrolls in a school of wizardry, where he learns the truth about himself, his family and the terrible evil that haunts the magical world.").build();

        Movie harryPotter2 = Movie.builder().name("Harry Potter and the Chamber of Secrets ").image(harryPotter2Image)
                .description("An ancient prophecy seems to be coming true when a mysterious presence begins stalking the corridors of a school of magic and leaving its victims paralyzed.").build();

        Movie harryPotter3 = Movie.builder().name("Harry Potter and the Prisoner of Azkaban ").image(harryPotter3Image)
                .description("Harry Potter, Ron and Hermione return to Hogwarts School of Witchcraft and Wizardry for their third year of study, " +
                        "where they delve into the mystery surrounding an escaped prisoner who poses a dangerous threat to the young wizard.").build();

        Movie harryPotter4 = Movie.builder().name("Harry Potter and the Goblet of Fire").image(harryPotter4Image)
                .description("Harry Potter finds himself competing in a hazardous tournament between rival schools of magic, but he is distracted by recurring nightmares.").build();

        Movie harryPotter5 = Movie.builder().name("Harry Potter and the Order of the Phoenix ").image(harryPotter5Image)
                .description("With their warning about Lord Voldemort's return scoffed at, Harry and Dumbledore are targeted by the Wizard authorities as an authoritarian bureaucrat slowly seizes power at Hogwarts.").build();

        Movie harryPotter6 = Movie.builder().name("Harry Potter and the Half-Blood Prince").image(harryPotter6Image)
                .description("As Harry Potter begins his sixth year at Hogwarts, he discovers an old book marked as the property of the Half-Blood Prince and begins to learn more about Lord Voldemort's dark past.").build();


        // Categories Images
        Byte[] fantasyImage = getBytes("src/main/resources/static/images/fantasy.jpg");

        Category fantasy = Category.builder().name("Fantasy").image(fantasyImage)
                .description("Fantasy films are films that belong to the fantasy genre with fantastic themes, usually magic, " +
                        "supernatural events, mythology, folklore, or exotic fantasy worlds")
                .movies(Arrays.asList(lordOfTheRings1, lordOfTheRings2, lordOfTheRings3, harryPotter1, harryPotter2, harryPotter3, harryPotter4, harryPotter5, harryPotter6)).build();

        lordOfTheRings1.setCategory(fantasy);
        lordOfTheRings2.setCategory(fantasy);
        lordOfTheRings3.setCategory(fantasy);
        harryPotter1.setCategory(fantasy);
        harryPotter2.setCategory(fantasy);
        harryPotter3.setCategory(fantasy);
        harryPotter4.setCategory(fantasy);
        harryPotter5.setCategory(fantasy);
        harryPotter6.setCategory(fantasy);


        categoryRepository.save(fantasy);
        movieRepository.save(lordOfTheRings1);
        movieRepository.save(lordOfTheRings2);
        movieRepository.save(lordOfTheRings3);
        movieRepository.save(harryPotter1);
        movieRepository.save(harryPotter2);
        movieRepository.save(harryPotter3);
        movieRepository.save(harryPotter4);
        movieRepository.save(harryPotter5);
        movieRepository.save(harryPotter6);


        Byte[] horrorImage = getBytes("src/main/resources/static/images/categories/horror.jpg");
        Byte[] actionImage = getBytes("src/main/resources/static/images/categories/action.jpg");
        Byte[] comedyImage = getBytes("src/main/resources/static/images/categories/comedy.jpg");
        Byte[] thrillerImage = getBytes("src/main/resources/static/images/categories/thriller.jpg");
        Byte[] animatedImage = getBytes("src/main/resources/static/images/categories/animated.jpg");
        Byte[] historicalImage = getBytes("src/main/resources/static/images/categories/historical.jpg");


        Category horror = Category.builder().name("Horror").image(horrorImage)
                .description("A horror film is a film that seeks to elicit fear for entertainment purposes " +
                                "Initially inspired by literature from authors such as Edgar Allan Poe, Bram Stoker, and Mary Shelley").build();


        Category action = Category.builder().name("Action").image(actionImage)
                .description("One of the earliest film genres in existence, the action genre has close ties to classic strife and struggle narratives that you find across all manner of art and literature").build();

        Category comedy = Category.builder().name("Comedy").image(comedyImage)
                .description("A favorite genre of film audiences young and old, from the very beginning of cinema, the comedy genre has been a fun-loving, quite sophisticated," +
                        " and innovative genre that’s delighted viewers for decades").build();

        Category thriller = Category.builder().name("Thriller").image(thrillerImage)
                .description("Once a stylized niche genre, the thriller film has gone so mainstream that it might be time to change the genre’s name to Summer Blockbuster Event. " +
                        "The thriller’s rise coincides with the rise of the spy and detective pulp novels of the 1960s and 1970s.").build();

        Category animated = Category.builder().name("Animated").image(animatedImage)
                .description("A computer-animated film is a feature film that has been computer-animated to appear three-dimensional. " +
                        "While traditional 2D animated films are now made primarily with the help of computers, " +
                        "the technique to render (CG) or (CGI), is unique to computers.").build();

        Category historical = Category.builder().name("Historical").image(historicalImage)
                .description("Historical Films often take an historical or imagined event, mythic, legendary, or heroic figure," +
                        " and add an extravagant setting and lavish costumes, accompanied by grandeur and spectacle and a sweeping musical score.").build();




        categoryRepository.save(horror);
        categoryRepository.save(action);
        categoryRepository.save(comedy);
        categoryRepository.save(thriller);
        categoryRepository.save(animated);
        categoryRepository.save(historical);





    }

    private void initScienceFiction() throws IOException {
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


        Byte[] scifiImage = getBytes("src/main/resources/static/images/sci-fi.jpg");


        Category sciFi = Category.builder().name("Sci-Fi").image(scifiImage)
                .movies(Arrays.asList(startWars1, startWars2, startWars3, startWars4, startWars5, startWars6, backToTheFuture1, backToTheFuture2, backToTheFuture3, fifthElement, matrix, interstellar))
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

        categoryRepository.save(sciFi);
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

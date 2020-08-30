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
import java.time.LocalDate;
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
    // Checking build
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {

        initScienceFiction();
        initFantasy();
        initComedy();
        initAnimated();


        Byte[] horrorImage = getBytes("src/main/resources/static/images/categories/horror.jpg");
        Byte[] actionImage = getBytes("src/main/resources/static/images/categories/action.jpg");
        Byte[] thrillerImage = getBytes("src/main/resources/static/images/categories/thriller.jpg");
        Byte[] historicalImage = getBytes("src/main/resources/static/images/categories/historical.jpg");


        Category horror = Category.builder().name("Horror").image(horrorImage)
                .description("A horror film is a film that seeks to elicit fear for entertainment purposes " +
                                "Initially inspired by literature from authors such as Edgar Allan Poe, Bram Stoker, and Mary Shelley").build();


        Category action = Category.builder().name("Action").image(actionImage)
                .description("One of the earliest film genres in existence, the action genre has close ties to classic strife and struggle narratives that you find across all manner of art and literature").build();



        Category thriller = Category.builder().name("Thriller").image(thrillerImage)
                .description("Once a stylized niche genre, the thriller film has gone so mainstream that it might be time to change the genre’s name to Summer Blockbuster Event. " +
                        "The thriller’s rise coincides with the rise of the spy and detective pulp novels of the 1960s and 1970s.").build();



        Category historical = Category.builder().name("Historical").image(historicalImage)
                .description("Historical Films often take an historical or imagined event, mythic, legendary, or heroic figure," +
                        " and add an extravagant setting and lavish costumes, accompanied by grandeur and spectacle and a sweeping musical score.").build();




        categoryRepository.save(horror);
        categoryRepository.save(action);

        categoryRepository.save(thriller);
        categoryRepository.save(historical);

    }

    private void initAnimated() throws IOException {
        Byte[] shrek_img = getBytes("src/main/resources/static/images/animated/shrek.jpg");
        Byte[] shrek2_img = getBytes("src/main/resources/static/images/animated/shrek2.jpg");
        Byte[] shrek3_img = getBytes("src/main/resources/static/images/animated/shrek3.jpg");
        Byte[] monsters_img = getBytes("src/main/resources/static/images/animated/monsters.jpg");
        Byte[] monstersUniversity_img = getBytes("src/main/resources/static/images/animated/monstersUniversity.jpg");
        Byte[] toyStory_img = getBytes("src/main/resources/static/images/animated/toyStory.jpg");
        Byte[] findingNemo_img = getBytes("src/main/resources/static/images/animated/findingNemo.jpg");
        Byte[] cars_img = getBytes("src/main/resources/static/images/animated/cars.jpg");
        Byte[] mulan_img = getBytes("src/main/resources/static/images/animated/mulan.jpg");
        Byte[] kungFuPanda_img = getBytes("src/main/resources/static/images/animated/kungFuPanda.jpg");
        Byte[] ratatouille_img = getBytes("src/main/resources/static/images/animated/ratatouille.jpg");
        Byte[] incredibles_img = getBytes("src/main/resources/static/images/animated/incredibles.jpg");


        Movie shrek = Movie.builder().name("Shrek").image(shrek_img).time(90L).year(2001).country("USA")
                .description("A mean lord exiles fairytale creatures to the swamp of a grumpy ogre, who must go on a quest and rescue a princess for the lord in order to get his land back.").build();

        Movie shrek2 = Movie.builder().name("Shrek 2").image(shrek2_img).time(100L).year(2004).country("USA")
                .description("Shrek and Fiona travel to the Kingdom of Far Far Away, where Fiona's parents are King and Queen," +
                                " to celebrate their marriage. When they arrive, they find they are not as welcome as they thought they would be.").build();

        Movie shrek3 = Movie.builder().name("Shrek the Third").image(shrek3_img).time(111L).year(2007).country("USA")
                .description("When his new father-in-law, King Harold falls ill, Shrek is looked at as the heir to the land of Far, Far Away." +
                        " Not one to give up his beloved swamp").build();

        Movie monsters = Movie.builder().name("Monsters, Inc.").image(monsters_img).time(92L).year(2001).country("USA")
                .description("In order to power the city, monsters have to scare children so that they scream. However, the children are toxic to the monsters," +
                        " and after a child gets through, 2 monsters realize things may not be what they think.").build();

        Movie monstersUniversity = Movie.builder().name("Monsters University").image(monstersUniversity_img).time(103L).year(2013).country("USA")
                .description("A look at the relationship between Mike Wazowski  and James P. Sully Sullivan  during their days at Monsters University, when they weren't necessarily the best of friends.").build();

        Movie toyStory = Movie.builder().name("Toy Story").image(toyStory_img).time(81L).year(1995).country("USA")
                .description("A cowboy doll is profoundly threatened and jealous when a new spaceman figure supplants him as top toy in a boy's room.").build();

        Movie findingNemo = Movie.builder().name("Finding Nemo").image(findingNemo_img).time(100L).year(2003).country("USA")
                .description("After his son is captured in the Great Barrier Reef and taken to Sydney, a timid clownfish sets out on a journey to bring him home.").build();

        Movie cars = Movie.builder().name("Cars").image(cars_img).time(92L).year(2006).country("USA")
                .description("A hot-shot race-car named Lightning McQueen gets waylaid in Radiator Springs, where he finds the true meaning of friendship and family").build();

        Movie mulan = Movie.builder().name("Mulan").image(mulan_img).time(88L).year(1998).country("USA")
                .description("To save her father from death in the army, a young maiden secretly goes in his place and becomes one of China's greatest heroines in the process.").build();

        Movie kungFuPanda = Movie.builder().name("Kung Fu Panda").image(kungFuPanda_img).time(92L).year(2008).country("USA")
                .description("The Dragon Warrior has to clash against the savage Tai Lung as China's fate hangs in the balance. " +
                        "However, the Dragon Warrior mantle is supposedly mistaken to be bestowed upon an obese panda who is a novice in martial arts").build();

        Movie ratatouille = Movie.builder().name("Ratatouille").image(ratatouille_img).time(93L).year(2007).country("USA")
                .description("A rat named Remy dreams of becoming a great French chef despite his family's wishes and the obvious problem of being a rat in a decidedly rodent-phobic profession.").build();

        Movie incredibles = Movie.builder().name("The Incredibles").image(incredibles_img).time(115L).year(2004).country("USA")
                .description("Bob Parr, and his wife Helen, are the world's greatest famous crime-fighting superheroes. Always saving lives and battling evil on a daily basis. But fifteen years later, " +
                        "they have been forced to adopt civilian identities and retreat to retire").build();


        Byte[] animatedImage = getBytes("src/main/resources/static/images/categories/animated.jpg");

        Category animated = Category.builder().name("Animated").image(animatedImage)
                .movies(Arrays.asList(shrek, shrek2, shrek3, monsters, monstersUniversity, toyStory, findingNemo, cars, mulan, kungFuPanda, ratatouille, incredibles))
                .description("A computer-animated film is a feature film that has been computer-animated to appear three-dimensional. " +
                        "While traditional 2D animated films are now made primarily with the help of computers, " +
                        "the technique to render (CG) or (CGI), is unique to computers.").build();


        shrek.setCategory(animated);
        shrek2.setCategory(animated);
        shrek3.setCategory(animated);
        monsters.setCategory(animated);
        monstersUniversity.setCategory(animated);
        toyStory.setCategory(animated);
        findingNemo.setCategory(animated);
        cars.setCategory(animated);
        mulan.setCategory(animated);
        kungFuPanda.setCategory(animated);
        ratatouille.setCategory(animated);
        incredibles.setCategory(animated);


        categoryRepository.save(animated);

        movieRepository.save(shrek);
        movieRepository.save(shrek2);
        movieRepository.save(shrek3);
        movieRepository.save(monsters);
        movieRepository.save(monstersUniversity);
        movieRepository.save(toyStory);
        movieRepository.save(findingNemo);
        movieRepository.save(cars);
        movieRepository.save(mulan);
        movieRepository.save(kungFuPanda);
        movieRepository.save(ratatouille);
        movieRepository.save(incredibles);
    }

    private void initComedy() throws IOException {
        Byte[] comedyImage = getBytes("src/main/resources/static/images/categories/comedy.jpg");

        Byte[] american_pie_img = getBytes("src/main/resources/static/images/comedy/american_pie.jpg");
        Byte[] hangover_img = getBytes("src/main/resources/static/images/comedy/hangover.jpg");
        Byte[] scary_movie_img = getBytes("src/main/resources/static/images/comedy/scary_movie.jpg");
        Byte[] truman_img = getBytes("src/main/resources/static/images/comedy/truman_show.jpg");
        Byte[] home_alone_img = getBytes("src/main/resources/static/images/comedy/home_alone.jpg");
        Byte[] blended_img = getBytes("src/main/resources/static/images/comedy/blended.jpg");
        Byte[] groundHog_img = getBytes("src/main/resources/static/images/comedy/groundhog.jpg");
        Byte[] happyGilmore_img = getBytes("src/main/resources/static/images/comedy/happy_gilmore.jpg");
        Byte[] comingAmerica_img = getBytes("src/main/resources/static/images/comedy/coming_america.jpg");
        Byte[] euro_trip_img = getBytes("src/main/resources/static/images/comedy/eurotrip.jpg");
        Byte[] shrunked_kids = getBytes("src/main/resources/static/images/comedy/shrunked_kids.jpg");
        Byte[] dumb_and_dumber_img = getBytes("src/main/resources/static/images/comedy/dumb_and_dumber.jpg");


        Movie americanPie = Movie.builder().name("American Pie").image(american_pie_img).time(95L).year(1999).country("USA")
                .description("Jim, Oz, Finch and Kevin are four friends who make a pact that before they graduate they will all lose their virginity. The hard job now is how to reach that goal by prom night.").build();

        Movie hangover = Movie.builder().name("The Hangover").image(hangover_img).time(100L).year(2009).country("USA")
                .description("Three buddies wake up from a bachelor party in Las Vegas, with no memory of the previous night and the bachelor missing. " +
                        "They make their way around the city in order to find their friend before his wedding.").build();

        Movie scaryMovie = Movie.builder().name("Scary Movie").image(scary_movie_img).time(88L).year(2000).country("USA")
                .description("A year after disposing of the body of a man they accidentally killed, a group of dumb teenagers are stalked by a bumbling serial killer.").build();

        Movie trumanShow = Movie.builder().name("The Truman Show").image(truman_img).time(103L).year(1998).country("USA")
                .description("Since birth, a big fat lie defines the well-organised but humdrum life of the kind-hearted insurance salesman and ambitious explorer, Truman Burbank. " +
                        "Utterly unaware of the thousands of cleverly hidden cameras watching his every move").build();

        Movie homeAlone = Movie.builder().name("Home Alone ").image(home_alone_img).time(103L).year(1992).country("USA")
                .description("An eight-year-old troublemaker must protect his house from a pair of burglars when he is accidentally left home alone by his family during Christmas vacation.").build();

        Movie blended = Movie.builder().name("Blended").image(blended_img).time(117L).year(2014).country("USA")
                .description("After a bad blind date, a man and woman find themselves stuck together at a resort for families, where their attraction grows as their respective kids benefit from the burgeoning relationship.").build();

        Movie groundhogDay = Movie.builder().name("Groundhog Day").image(groundHog_img).time(101L).year(1993).country("USA")
                .description("A weather man is reluctantly sent to cover a story about a weather forecasting. On awaking the 'following' day he discovers that it's Groundhog Day again, and again, and again. ").build();

        Movie happyGilmore = Movie.builder().name("Happy Gilmore").image(happyGilmore_img).time(92L).year(1996).country("USA")
                .description("A Hockey player wannabe finds out that he has the most powerful golf drive in history. He joins the P.G.A. tour to make some money to save grandma's house. " +
                        "The downside is that his hockey player mentality doesn't really go on the P.G.A. tour").build();

        Movie comingToAmerica = Movie.builder().name("Coming to America").image(comingAmerica_img).time(114L).year(1988).country("USA")
                .description("Immersed in luxury and riches, the courteous blue blood and refined heir apparent to Africa's prosperous kingdom of Zamunda, " +
                        "Prince Akeem, summons up the courage to reject an arranged marriage proposal on his twenty-first birthday.").build();

        Movie euroTrip = Movie.builder().name("Euro Trip").image(euro_trip_img).time(117L).year(2004).country("USA")
                .description("Ohio high school student Scott Thomas - who is about to graduate and go into pre-med. " +
                        "Dumped by his girlfriend, a high school grad decides to embark on an overseas adventure in Europe with his friends.").build();

        Movie shrunkKids = Movie.builder().name("Honey, I Shrunk the Kids").image(shrunked_kids).time(93L).year(1989).country("USA")
                .description("The scientist father of a teenage girl and boy accidentally shrinks his and two other neighborhood teens to the size of insects." +
                        " Now the teens must fight diminutive dangers as the father searches for them.").build();

        Movie dumbAndDumber = Movie.builder().name("Dumb and Dumber").image(dumb_and_dumber_img).time(107L).year(1994).country("USA")
                .description("After a woman leaves a briefcase at the airport terminal, a dumb limo driver and his dumber friend set out on a hilarious cross-country road trip to Aspen to return it.").build();


        Category comedy = Category.builder().name("Comedy").image(comedyImage)
                .movies(Arrays.asList(americanPie, hangover, scaryMovie, trumanShow, homeAlone, blended, groundhogDay, happyGilmore, comingToAmerica, euroTrip, shrunkKids, dumbAndDumber))
                .description("A favorite genre of film audiences young and old, from the very beginning of cinema, the comedy genre has been a fun-loving, quite sophisticated," +
                        " and innovative genre that’s delighted viewers for decades").build();

        americanPie.setCategory(comedy);
        hangover.setCategory(comedy);
        scaryMovie.setCategory(comedy);
        trumanShow.setCategory(comedy);
        homeAlone.setCategory(comedy);
        blended.setCategory(comedy);
        groundhogDay.setCategory(comedy);
        happyGilmore.setCategory(comedy);
        comingToAmerica.setCategory(comedy);
        euroTrip.setCategory(comedy);
        shrunkKids.setCategory(comedy);
        dumbAndDumber.setCategory(comedy);

        categoryRepository.save(comedy);

        movieRepository.save(americanPie);
        movieRepository.save(hangover);
        movieRepository.save(scaryMovie);
        movieRepository.save(trumanShow);
        movieRepository.save(homeAlone);
        movieRepository.save(blended);
        movieRepository.save(groundhogDay);
        movieRepository.save(happyGilmore);
        movieRepository.save(comingToAmerica);
        movieRepository.save(euroTrip);
        movieRepository.save(shrunkKids);
        movieRepository.save(dumbAndDumber);
    }

    private void initFantasy() throws IOException {
        // The Lord Of The Rings
        Byte[] lordOfTheRings1Image = getBytes("src/main/resources/static/images/fantasy/lord_of_the_rings/part1.jpg");
        Byte[] lordOfTheRings2Image = getBytes("src/main/resources/static/images/fantasy/lord_of_the_rings/part2.jpg");
        Byte[] lordOfTheRings3Image = getBytes("src/main/resources/static/images/fantasy/lord_of_the_rings/part3.jpg");

        Movie lordOfTheRings1 = Movie.builder().name("The Lord of the Rings: Fellowship of the rings").image(lordOfTheRings1Image).time(228L).year(2001).country("New Zealand")
                .description("A meek Hobbit from the Shire and eight companions set out on a journey to destroy the powerful One Ring and save Middle-earth from the Dark Lord Sauron.").build();

        Movie lordOfTheRings2 = Movie.builder().name("The Lord of the Rings: Two Towers").image(lordOfTheRings2Image).time(223L).year(2002).country("New Zealand")
                .description("While Frodo and Sam edge closer to Mordor with the help of the shifty Gollum, the divided fellowship makes a stand against Sauron's new ally, Saruman, and his hordes of Isengard.").build();

        Movie lordOfTheRings3 = Movie.builder().name("The Lord of the Rings: The Return of the King").image(lordOfTheRings3Image).time(251L).year(2003).country("New Zealand")
                .description("Gandalf and Aragorn lead the World of Men against Sauron's army to draw his gaze from Frodo and Sam as they approach Mount Doom with the One Ring.").build();

        // Harry Potter
        Byte[] harryPotter1Image = getBytes("src/main/resources/static/images/fantasy/harryPotter/part1.jpg");
        Byte[] harryPotter2Image = getBytes("src/main/resources/static/images/fantasy/harryPotter/part2.jpg");
        Byte[] harryPotter3Image = getBytes("src/main/resources/static/images/fantasy/harryPotter/part3.jpg");
        Byte[] harryPotter4Image = getBytes("src/main/resources/static/images/fantasy/harryPotter/part4.jpg");
        Byte[] harryPotter5Image = getBytes("src/main/resources/static/images/fantasy/harryPotter/part5.jpg");
        Byte[] harryPotter6Image = getBytes("src/main/resources/static/images/fantasy/harryPotter/part6.jpg");
        Byte[] harryPotter7Image = getBytes("src/main/resources/static/images/fantasy/harryPotter/part7.jpg");
        Byte[] harryPotter8Image = getBytes("src/main/resources/static/images/fantasy/harryPotter/part8.jpg");

        Movie harryPotter1 = Movie.builder().name("Harry Potter and the Sorcerer's Stone").image(harryPotter1Image).time(154L).year(2001).country("Great Britain")
                .description("An orphaned boy enrolls in a school of wizardry, where he learns the truth about himself, his family and the terrible evil that haunts the magical world.").build();

        Movie harryPotter2 = Movie.builder().name("Harry Potter and the Chamber of Secrets ").image(harryPotter2Image).time(174L).year(2002).country("Great Britain")
                .description("An ancient prophecy seems to be coming true when a mysterious presence begins stalking the corridors of a school of magic and leaving its victims paralyzed.").build();

        Movie harryPotter3 = Movie.builder().name("Harry Potter and the Prisoner of Azkaban ").image(harryPotter3Image).time(142L).year(2004).country("Great Britain")
                .description("Harry Potter, Ron and Hermione return to Hogwarts School of Witchcraft and Wizardry for their third year of study, " +
                        "where they delve into the mystery surrounding an escaped prisoner who poses a dangerous threat to the young wizard.").build();

        Movie harryPotter4 = Movie.builder().name("Harry Potter and the Goblet of Fire").image(harryPotter4Image).time(157L).year(2005).country("Great Britain")
                .description("Harry Potter finds himself competing in a hazardous tournament between rival schools of magic, but he is distracted by recurring nightmares.").build();

        Movie harryPotter5 = Movie.builder().name("Harry Potter and the Order of the Phoenix ").image(harryPotter5Image).time(142L).year(2007).country("Great Britain")
                .description("With their warning about Lord Voldemort's return scoffed at, Harry and Dumbledore are targeted by the Wizard authorities as an authoritarian bureaucrat slowly seizes power at Hogwarts.").build();

        Movie harryPotter6 = Movie.builder().name("Harry Potter and the Half-Blood Prince").image(harryPotter6Image).time(154L).year(2009).country("Great Britain")
                .description("As Harry Potter begins his sixth year at Hogwarts, he discovers an old book marked as the property of the Half-Blood Prince and begins to learn more about Lord Voldemort's dark past.").build();


        Movie harryPotter7 = Movie.builder().name("Harry Potter and the Deathly Hallows: Part 1").image(harryPotter7Image).time(146L).year(2010).country("Great Britain")
                .description("As Harry, Ron and Hermione race against time and evil to destroy the Horcruxes, they uncover the existence of the three most powerful objects in the wizarding world: the Deathly Hallows.").build();


        Movie harryPotter8 = Movie.builder().name("Harry Potter and the Deathly Hallows: Part 2").image(harryPotter8Image).time(130L).year(2011).country("Great Britain")
                .description("Harry, Ron, and Hermione search for Voldemort's remaining Horcruxes in their effort to destroy the Dark Lord as the final battle rages on at Hogwarts.").build();



        // Categories Images
        Byte[] fantasyImage = getBytes("src/main/resources/static/images/fantasy.jpg");

        Category fantasy = Category.builder().name("Fantasy").image(fantasyImage)
                .description("Fantasy films are films that belong to the fantasy genre with fantastic themes, usually magic, " +
                        "supernatural events, mythology, folklore, or exotic fantasy worlds")
                .movies(Arrays.asList(lordOfTheRings1, lordOfTheRings2, lordOfTheRings3, harryPotter1, harryPotter2, harryPotter3, harryPotter4, harryPotter5, harryPotter6, harryPotter7, harryPotter8)).build();

        lordOfTheRings1.setCategory(fantasy);
        lordOfTheRings2.setCategory(fantasy);
        lordOfTheRings3.setCategory(fantasy);
        harryPotter1.setCategory(fantasy);
        harryPotter2.setCategory(fantasy);
        harryPotter3.setCategory(fantasy);
        harryPotter4.setCategory(fantasy);
        harryPotter5.setCategory(fantasy);
        harryPotter6.setCategory(fantasy);
        harryPotter7.setCategory(fantasy);
        harryPotter8.setCategory(fantasy);



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
        movieRepository.save(harryPotter7);
        movieRepository.save(harryPotter8);
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

        // Star Wars 1
        Byte[] liamNeesonImage = getBytes("src/main/resources/static/images/actors/starwars/liam_neeson.jpg");
        Byte[] ewanMcGregorImage = getBytes("src/main/resources/static/images/actors/starwars/ewan_mcgregor.jpg");
        Byte[] nataliePortmanImage = getBytes("src/main/resources/static/images/actors/starwars/natalie_portman.jpg");
        Byte[] jakeLloydImage = getBytes("src/main/resources/static/images/actors/starwars/jake_lloyd.jpg");
        Byte[] ianMcDiarnidImage = getBytes("src/main/resources/static/images/actors/starwars/ian_mc.jpg");

        // Qui-Gon Jinn
        Actor liamNeeson = Actor.builder().firstName("Liam").lastName("Neeson").image(liamNeesonImage).dateOfBirth(LocalDate.of(1952,6,7)).build();
        // Obi-Wan Kenobi young
        Actor ewanMcGregor = Actor.builder().firstName("Ewan").lastName("McGregor").image(ewanMcGregorImage).dateOfBirth(LocalDate.of(1971,3,31)).build();
        //Padmé Amidala
        Actor nataliePortman = Actor.builder().firstName("Natalie").lastName("Portman").image(nataliePortmanImage).dateOfBirth(LocalDate.of(1981,6,9)).build();
        // Anakin Skywalker kid
        Actor jakeLloyd = Actor.builder().firstName("Jacob").lastName("Lloyd").image(jakeLloydImage).dateOfBirth(LocalDate.of(1989,3,5)).build();
        // Senator Sheev Palpatine
        Actor ianMcDiarnid = Actor.builder().firstName("Ian").lastName("McDiarmid").image(ianMcDiarnidImage).dateOfBirth(LocalDate.of(1944,8,11)).build();


        Movie startWars1 = Movie.builder().name("Star Wars: Phantom Menace").time(136L).year(1999).country("USA")
                .actors(Arrays.asList(liamNeeson, ewanMcGregor, nataliePortman, jakeLloyd, ianMcDiarnid))
                .description("Two Jedi escape a hostile blockade to find allies and come across a young boy who may bring balance to the Force, " +
                "but the long dormant Sith resurface to claim their original glory.").image(starWars1Image).build();

        liamNeeson.getMovies().add(startWars1);
        ewanMcGregor.getMovies().add(startWars1);
        nataliePortman.getMovies().add(startWars1);
        jakeLloyd.getMovies().add(startWars1);
        ianMcDiarnid.getMovies().add(startWars1);


        // Star Wars 2
        Byte[] haydenChristensenImage = getBytes("src/main/resources/static/images/actors/starwars/hayden-christensen.jpg");
        Byte[] samuelLJacksonImage = getBytes("src/main/resources/static/images/actors/starwars/samuel.jpg");

        // Anakin Skywalker
        Actor haydenChristensen = Actor.builder().firstName("Hayden").lastName("Christensen").image(haydenChristensenImage).dateOfBirth(LocalDate.of(1981,4,19)).build();
        // Mace Windu
        Actor samuelLJackson = Actor.builder().firstName("Samuel").lastName("L. Jackson").image(samuelLJacksonImage).dateOfBirth(LocalDate.of(1948,12,21)).build();

        Movie startWars2 = Movie.builder().name("Star Wars: Attack Of The Clones").time(142L).year(2002).country("USA")
                .actors(Arrays.asList(ianMcDiarnid, ewanMcGregor, haydenChristensen, samuelLJackson, ianMcDiarnid))
                .description("Ten years after initially meeting, Anakin Skywalker shares a forbidden romance with Padmé Amidala, " +
                        "while Obi-Wan Kenobi investigates an assassination attempt on the senator and discovers a secret clone army crafted for the Jedi").image(starWars2Image).build();

        ewanMcGregor.getMovies().add(startWars2);
        nataliePortman.getMovies().add(startWars2);
        haydenChristensen.getMovies().add(startWars2);
        samuelLJackson.getMovies().add(startWars2);
        ianMcDiarnid.getMovies().add(startWars2);

        // Star Wars 3
        Movie startWars3 = Movie.builder().name("Star Wars: Revenge of the Sith").time(140L).year(2005).country("USA")
                .actors(Arrays.asList(ianMcDiarnid, ewanMcGregor, haydenChristensen, samuelLJackson, ianMcDiarnid))
                .description("Three years into the Clone Wars, the Jedi rescue Palpatine from Count Dooku. As Obi-Wan pursues a new threat," +
                " Anakin acts as a double agent between the Jedi Council and Palpatine and is lured into a sinister plan to rule the galaxy").image(starWars3Image).build();

        ewanMcGregor.getMovies().add(startWars3);
        nataliePortman.getMovies().add(startWars3);
        haydenChristensen.getMovies().add(startWars3);
        samuelLJackson.getMovies().add(startWars3);
        ianMcDiarnid.getMovies().add(startWars3);


        // Star Wars 4
        Byte[] markHamillImage = getBytes("src/main/resources/static/images/actors/starwars/mark_hammil.jpg");
        Byte[] harrisonFordImage = getBytes("src/main/resources/static/images/actors/starwars/harrison_ford.jpg");
        Byte[] carrieFisherImage = getBytes("src/main/resources/static/images/actors/starwars/carrie_fisher.jpg");
        Byte[] anthonyDanielsImage = getBytes("src/main/resources/static/images/actors/starwars/c3po.jpg");
        Byte[] kennyBakerImage = getBytes("src/main/resources/static/images/actors/starwars/r2d2.jpg");

        // Luke Skywalker
        Actor markHamill = Actor.builder().firstName("Mark").lastName("Hamill").image(markHamillImage).dateOfBirth(LocalDate.of(1951,9,25)).build();
        // Han Solo
        Actor harrisonFord = Actor.builder().firstName("Harrison").lastName("Ford").image(harrisonFordImage).dateOfBirth(LocalDate.of(1942,7,13)).build();
        // Leya
        Actor carrieFisher = Actor.builder().firstName("Carrie").lastName("Fisher").image(carrieFisherImage).dateOfBirth(LocalDate.of(1956,10,21)).build();
        // C3PO
        Actor anthonyDaniels = Actor.builder().firstName("Anthony").lastName("Daniels").image(anthonyDanielsImage).dateOfBirth(LocalDate.of(1946,2,21)).build();
        // R2D2
        Actor kennyBaker = Actor.builder().firstName("Kenny").lastName("Baker").image(kennyBakerImage).dateOfBirth(LocalDate.of(1934,9,24)).build();


        Movie startWars4 = Movie.builder().name("Star Wars: A New Hope").time(125L).year(1977).country("USA")
                .actors(Arrays.asList(markHamill, harrisonFord, carrieFisher, anthonyDaniels, kennyBaker))
                .description("Luke Skywalker joins forces with a Jedi Knight, a cocky pilot, a Wookiee and two droids to save the galaxy from the Empire's world-destroying battle station, " +
                "while also attempting to rescue Princess Leia from the mysterious Darth Vader").image(starWars4Image).build();

        ewanMcGregor.getMovies().add(startWars4);
        nataliePortman.getMovies().add(startWars4);
        haydenChristensen.getMovies().add(startWars4);
        samuelLJackson.getMovies().add(startWars4);
        ianMcDiarnid.getMovies().add(startWars4);

        // Star Wars 5
        Movie startWars5 = Movie.builder().name("Star Wars: The Empire Strikes Back").time(127L).year(1980).country("USA")
                .actors(Arrays.asList(markHamill, harrisonFord, carrieFisher, anthonyDaniels, kennyBaker))
                .description("After the Rebels are brutally overpowered by the Empire on the ice planet Hoth, Luke Skywalker begins Jedi training with Yoda," +
                " while his friends are pursued by Darth Vader and a bounty hunter named Boba Fett all over the galaxy").image(starWars5Image).build();

        ewanMcGregor.getMovies().add(startWars5);
        nataliePortman.getMovies().add(startWars5);
        haydenChristensen.getMovies().add(startWars5);
        samuelLJackson.getMovies().add(startWars5);
        ianMcDiarnid.getMovies().add(startWars5);

        // Star Wars 6
        Movie startWars6 = Movie.builder().name("Star Wars Return of the the Jedi").time(136L).year(1983).country("USA")
                .actors(Arrays.asList(markHamill, harrisonFord, carrieFisher, anthonyDaniels, kennyBaker))
                .description("After a daring mission to rescue Han Solo from Jabba the Hutt, the Rebels dispatch to Endor to destroy the second Death Star. " +
                "Meanwhile, Luke struggles to help Darth Vader back from the dark side without falling into the Emperor's trap.").image(starWars6Image).build();

        ewanMcGregor.getMovies().add(startWars6);
        nataliePortman.getMovies().add(startWars6);
        haydenChristensen.getMovies().add(startWars6);
        samuelLJackson.getMovies().add(startWars6);
        ianMcDiarnid.getMovies().add(startWars6);
        // END OF STAR WARS

        // I finished here with actors - exhausted xD

        // Back to the Future Images
        Byte[] backToTheFuture1Image = getBytes("src/main/resources/static/images/science-fiction/back_to_the_future/part1.jpg");
        Byte[] backToTheFuture2Image = getBytes("src/main/resources/static/images/science-fiction/back_to_the_future/part2.jpg");
        Byte[] backToTheFuture3Image = getBytes("src/main/resources/static/images/science-fiction/back_to_the_future/part3.jpg");

        // Back to the future movies
        Byte[] christopherLLoydImage = getBytes("src/main/resources/static/images/actors/back_to_future/christopherLloyd.jpg");
        Byte[] michaelFoxImage = getBytes("src/main/resources/static/images/actors/back_to_future/michaelFox.jpg");
        Byte[] thomasWilsonImage = getBytes("src/main/resources/static/images/actors/back_to_future/thomasWilson.jpg");
        Byte[] crispingGloverImage = getBytes("src/main/resources/static/images/actors/back_to_future/crispingGlover.jpg");
        Byte[] leaThompsonImage = getBytes("src/main/resources/static/images/actors/back_to_future/leaThompson.jpg");

        // Dr Emmett Brown
        Actor christopherLLoyd = Actor.builder().firstName("Christopher").lastName("Lloyd").image(christopherLLoydImage).dateOfBirth(LocalDate.of(1938,11,22)).build();
        // Marty McFly
        Actor michaelFox = Actor.builder().firstName("Michael").lastName("Fox").image(michaelFoxImage).dateOfBirth(LocalDate.of(1961,6,9)).build();
        // Biff Tannen
        Actor thomasWilson = Actor.builder().firstName("Thomas").lastName("Wilson").image(thomasWilsonImage).dateOfBirth(LocalDate.of(1959,4,15)).build();
        // George McFly
        Actor crispingGlover = Actor.builder().firstName("Crispin").lastName("Glover").image(crispingGloverImage).dateOfBirth(LocalDate.of(1964,4,20)).build();
        // Lorraine Baines
        Actor leaThompson = Actor.builder().firstName("Lea").lastName("Thompson").image(leaThompsonImage).dateOfBirth(LocalDate.of(1961,5,31)).build();


        Movie backToTheFuture1 = Movie.builder().name("Back To The Future Part 1").time(111L).year(1985).country("USA")
                .actors(Arrays.asList(christopherLLoyd, michaelFox, thomasWilson, crispingGlover, leaThompson))
                .description("Marty McFly, a 17-year-old high school student, is accidentally sent thirty years into the past in a time-traveling DeLorean " +
                "invented by his close friend, the eccentric scientist Doc Brown.")
                .image(backToTheFuture1Image).build();

        Movie backToTheFuture2 = Movie.builder().name("Back To The Future Part 2").time(108L).year(1989).country("USA")
                .actors(Arrays.asList(christopherLLoyd, michaelFox, thomasWilson, crispingGlover, leaThompson))
                .description("After visiting 2015, Marty McFly must repeat his visit to 1955 to prevent disastrous changes to 1985..." +
                "without interfering with his first trip")
                .image(backToTheFuture2Image).build();

        Movie backToTheFuture3 = Movie.builder().name("Back To The Future Part 3").time(119L).year(1990).country("USA")
                .actors(Arrays.asList(christopherLLoyd, michaelFox, thomasWilson, crispingGlover, leaThompson))
                .description("Stranded in 1955, Marty McFly learns about the death of Doc Brown in 1885 and must travel back in time to save him." +
                " With no fuel readily available for the DeLorean, the two must figure how to escape the Old West before Emmett is murdered.")
                .image(backToTheFuture3Image).build();

        christopherLLoyd.getMovies().addAll(Arrays.asList(backToTheFuture1, backToTheFuture2, backToTheFuture3));
        michaelFox.getMovies().addAll(Arrays.asList(backToTheFuture1, backToTheFuture2, backToTheFuture3));
        thomasWilson.getMovies().addAll(Arrays.asList(backToTheFuture1, backToTheFuture2, backToTheFuture3));
        crispingGlover.getMovies().addAll(Arrays.asList(backToTheFuture1, backToTheFuture2, backToTheFuture3));
        leaThompson.getMovies().addAll(Arrays.asList(backToTheFuture1, backToTheFuture2, backToTheFuture3));


        // Matrix
        Byte[] keanuReevesImage = getBytes("src/main/resources/static/images/actors/matrix/keanuReeves.jpg");
        Byte[] lauranceFishburneImage = getBytes("src/main/resources/static/images/actors/matrix/lauranceFishborne.jpg");
        Byte[] carrieMossImage = getBytes("src/main/resources/static/images/actors/matrix/carrieMoss.jpg");
        Byte[] hugoWeavingImage = getBytes("src/main/resources/static/images/actors/matrix/hugoWeaving.jpg");

        // Neo
        Actor keanuReeves = Actor.builder().firstName("Keanu").lastName("Reeves").image(keanuReevesImage).dateOfBirth(LocalDate.of(1964,9,2)).build();
        // Morpheus
        Actor lauranceFishburne = Actor.builder().firstName("Laurence").lastName("Fishburne").image(lauranceFishburneImage).dateOfBirth(LocalDate.of(1961,7,3)).build();
        // Trinity
        Actor carrieMoss = Actor.builder().firstName("Carrie-Anne").lastName("Moss").image(carrieMossImage).dateOfBirth(LocalDate.of(1967,9,21)).build();
        // Smith
        Actor hugoWeaving = Actor.builder().firstName("Hugo").lastName("Weaving").image(hugoWeavingImage).dateOfBirth(LocalDate.of(1960,4,4)).build();


        Byte[] matrix1Pict = getBytes("src/main/resources/static/images/science-fiction/matrix/part1.jpg");
        Byte[] matrix2Pict = getBytes("src/main/resources/static/images/science-fiction/matrix/part2.jpg");
        Byte[] matrix3Pict = getBytes("src/main/resources/static/images/science-fiction/matrix/part3.jpg");

        Movie matrix = Movie.builder().name("The Matrix").time(150L).year(1999).country("USA")
                .actors(Arrays.asList(keanuReeves, lauranceFishburne, carrieMoss, hugoWeaving))
                .description("A computer hacker learns from mysterious rebels about the true nature of his reality and his role in the war against its controllers.")
                .image(matrix1Pict).build();

        Movie matrix2 = Movie.builder().name("The Matrix Reloaded").time(138L).year(2003).country("USA")
                .actors(Arrays.asList(keanuReeves, lauranceFishburne, carrieMoss, hugoWeaving))
                .description("Neo and his allies race against time before the machines discover the city of Zion and destroy it. While seeking the truth about the Matrix, Neo must save Trinity from a dark fate within his dreams.")
                .image(matrix2Pict).build();

        Movie matrix3 = Movie.builder().name("The Matrix Revolutions").time(129L).year(2005).country("USA")
                .actors(Arrays.asList(keanuReeves, lauranceFishburne, carrieMoss, hugoWeaving))
                .description("The human city of Zion defends itself against the massive invasion of the machines as Neo fights to end the war at another front while also opposing the rogue Agent Smith.")
                .image(matrix3Pict).build();

        keanuReeves.getMovies().addAll(Arrays.asList(matrix, matrix2, matrix3));
        lauranceFishburne.getMovies().addAll(Arrays.asList(matrix, matrix2, matrix3));
        carrieMoss.getMovies().addAll(Arrays.asList(matrix, matrix2, matrix3));
        hugoWeaving.getMovies().addAll(Arrays.asList(matrix, matrix2, matrix3));



        // The Fifth Element
        Byte[] bruceWillisImage = getBytes("src/main/resources/static/images/actors/fifth_element/bruceWillis.jpg");
        Byte[] garryOldmanImage = getBytes("src/main/resources/static/images/actors/fifth_element/garyOldman.jpg");
        Byte[] chrisTuckerImage = getBytes("src/main/resources/static/images/actors/fifth_element/chrisTucker.jpg");
        Byte[] millaJovovichImage = getBytes("src/main/resources/static/images/actors/fifth_element/milla.jpg");

        // Korben Dallas
        Actor bruceWillis = Actor.builder().firstName("Bruce").lastName("Willis").image(bruceWillisImage).dateOfBirth(LocalDate.of(1955,3,19)).build();
        // Jean-Baptiste Emanuel Zorg
        Actor garryOldman = Actor.builder().firstName("Gary").lastName("Oldman").image(garryOldmanImage).dateOfBirth(LocalDate.of(1958,3,21)).build();
        // Ruby Rhod
        Actor chrisTucker = Actor.builder().firstName("Chris").lastName("Tucker").image(chrisTuckerImage).dateOfBirth(LocalDate.of(1972,9,29)).build();
        // Leeloo
        Actor millaJovovich = Actor.builder().firstName("Milla").lastName("Jovovich").image(millaJovovichImage).dateOfBirth(LocalDate.of(1975,12,17)).build();

        Byte[] fifthElementPict = getBytes("src/main/resources/static/images/science-fiction/fifthElement.jpg");

        Movie fifthElement = Movie.builder().name("The Fifth Element").time(127L).year(1997).country("France")
                .actors(Arrays.asList(bruceWillis, garryOldman, chrisTucker, millaJovovich))
                .description("In the colorful future, a cab driver unwittingly becomes the central figure in the search for a legendary cosmic weapon to keep Evil and Mr. Zorg at bay")
                .image(fifthElementPict).build();

        bruceWillis.getMovies().add(fifthElement);
        garryOldman.getMovies().add(fifthElement);
        chrisTucker.getMovies().add(fifthElement);
        millaJovovich.getMovies().add(fifthElement);


        // Interstellar
        Byte[] matthewMcconaugheyImage = getBytes("src/main/resources/static/images/actors/interstellar/mathew.jpg");
        Byte[] anneHathawayImage = getBytes("src/main/resources/static/images/actors/interstellar/anneHathaway.jpg");
        Byte[] jessicaChastainImage = getBytes("src/main/resources/static/images/actors/interstellar/jessicaChastain.jpg");
        Byte[] billIrwinImage = getBytes("src/main/resources/static/images/actors/interstellar/billIrwin.jpg");

        // Cooper
        Actor matthewMcconaughey = Actor.builder().firstName("Matthew").lastName("McConaughey").image(matthewMcconaugheyImage).dateOfBirth(LocalDate.of(1969,11,4)).build();
        // Brand
        Actor anneHathaway = Actor.builder().firstName("Anne").lastName("Hathaway").image(anneHathawayImage).dateOfBirth(LocalDate.of(1982,11,12)).build();
        // Murph
        Actor jessicaChastain = Actor.builder().firstName("Jessica").lastName("Chastain").image(jessicaChastainImage).dateOfBirth(LocalDate.of(1977,3,24)).build();
        // TARS
        Actor billIrwin = Actor.builder().firstName("Bill").lastName("Irwin").image(billIrwinImage).dateOfBirth(LocalDate.of(1950,4,11)).build();


        Byte[] interstellarPict = getBytes("src/main/resources/static/images/science-fiction/interstellar.jpg");

        Movie interstellar = Movie.builder().name("Interstellar").time(169L).year(2014).country("USA")
                .actors(Arrays.asList(matthewMcconaughey, anneHathaway, jessicaChastain, billIrwin))
                .description("A team of explorers travel through a wormhole in space in an attempt to ensure humanity's survival.")
                .image(interstellarPict).build();

        matthewMcconaughey.getMovies().add(interstellar);
        anneHathaway.getMovies().add(interstellar);
        jessicaChastain.getMovies().add(interstellar);
        billIrwin.getMovies().add(interstellar);


        Byte[] scifiImage = getBytes("src/main/resources/static/images/sci-fi.jpg");


        Category sciFi = Category.builder().name("Sci-Fi").image(scifiImage)
                .movies(Arrays.asList(startWars1, startWars2, startWars3, startWars4, startWars5, startWars6, backToTheFuture1, backToTheFuture2, backToTheFuture3, matrix, matrix2, matrix3, fifthElement, interstellar))
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
        matrix2.setCategory(sciFi);
        matrix3.setCategory(sciFi);
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
        movieRepository.save(matrix);
        movieRepository.save(matrix2);
        movieRepository.save(matrix3);
        movieRepository.save(interstellar);
        movieRepository.save(fifthElement);

        actorRepository.save(liamNeeson);
        actorRepository.save(ewanMcGregor);
        actorRepository.save(nataliePortman);
        actorRepository.save(jakeLloyd);
        actorRepository.save(ianMcDiarnid);
        actorRepository.save(haydenChristensen);
        actorRepository.save(samuelLJackson);
        actorRepository.save(markHamill);
        actorRepository.save(harrisonFord);
        actorRepository.save(carrieFisher);
        actorRepository.save(anthonyDaniels);
        actorRepository.save(kennyBaker);
        actorRepository.save(christopherLLoyd);
        actorRepository.save(michaelFox);
        actorRepository.save(thomasWilson);
        actorRepository.save(crispingGlover);
        actorRepository.save(leaThompson);
        actorRepository.save(keanuReeves);
        actorRepository.save(lauranceFishburne);
        actorRepository.save(carrieMoss);
        actorRepository.save(hugoWeaving);
        actorRepository.save(matthewMcconaughey);
        actorRepository.save(anneHathaway);
        actorRepository.save(jessicaChastain);
        actorRepository.save(billIrwin);
        actorRepository.save(bruceWillis);
        actorRepository.save(garryOldman);
        actorRepository.save(chrisTucker);
        actorRepository.save(millaJovovich);
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

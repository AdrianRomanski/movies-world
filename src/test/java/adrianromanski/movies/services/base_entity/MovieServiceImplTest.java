package adrianromanski.movies.services.base_entity;


import adrianromanski.movies.domain.award.MovieAward;
import adrianromanski.movies.domain.base_entity.Category;
import adrianromanski.movies.domain.base_entity.Movie;
import adrianromanski.movies.domain.person.Actor;
import adrianromanski.movies.domain.person.User;
import adrianromanski.movies.domain.review.MovieReview;
import adrianromanski.movies.exceptions.EmptyListException;
import adrianromanski.movies.exceptions.ResourceNotFoundException;
import adrianromanski.movies.jms.JmsTextMessageService;
import adrianromanski.movies.mapper.award.MovieAwardMapper;
import adrianromanski.movies.mapper.award.MovieAwardMapperImpl;
import adrianromanski.movies.mapper.base_entity.CategoryMapper;
import adrianromanski.movies.mapper.base_entity.CategoryMapperImpl;
import adrianromanski.movies.mapper.base_entity.MovieMapper;
import adrianromanski.movies.mapper.base_entity.MovieMapperImpl;
import adrianromanski.movies.model.award.MovieAwardDTO;
import adrianromanski.movies.model.base_entity.MovieDTO;
import adrianromanski.movies.repositories.award.AwardRepository;
import adrianromanski.movies.repositories.base_entity.CategoryRepository;
import adrianromanski.movies.repositories.base_entity.MovieRepository;
import adrianromanski.movies.repositories.pages.MoviePageRepository;
import adrianromanski.movies.repositories.person.ActorRepository;
import adrianromanski.movies.services.movie.MovieService;
import adrianromanski.movies.services.movie.MovieServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDate;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class MovieServiceImplTest {

    public static final String STAR_WARS = "Star Wars";
    public static final String GOOD_MOVIE = "Good movie";
    public static final String CATEGORY = "Hottest movie";
    public static final String COUNTRY = "Poland";
    public static final LocalDate DATE = LocalDate.now();
    public static final String INDIANA_JONES = "IndianaJones";
    public static final String TESTING_DESCRIPTION = "Test Test Test Test Test Test Test Test Test Test Test Test Test Test";

    @Mock
    MovieRepository movieRepository;

    @Mock
    MoviePageRepository moviePageRepository;

    @Mock
    ActorRepository actorRepository;

    @Mock
    AwardRepository awardRepository;

    @Mock
    CategoryRepository categoryRepository;

    @Mock
    JmsTextMessageService jms;

    MovieService movieService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        MovieAwardMapper awardMapper = new MovieAwardMapperImpl();
        CategoryMapper categoryMapper = new CategoryMapperImpl();
        MovieMapper movieMapper = new MovieMapperImpl(); // I have to do something with this ugly init - That should be singleton!!!!!
        movieService = new MovieServiceImpl(movieRepository, moviePageRepository, categoryRepository, actorRepository,awardRepository, jms,
                                            movieMapper, categoryMapper, awardMapper);
    }

    private List<Movie> getMovies() { return Arrays.asList(new Movie(), new Movie(), new Movie()); }

    private Movie getStarWars() {
        Set<User> userFavourites = new HashSet<>(Arrays.asList(new User(), new User(), new User(), new User()));
        Set<User> userWatched = new HashSet<>(Arrays.asList(new User(), new User(), new User(), new User()));
        List<MovieReview> reviews = Arrays.asList(MovieReview.builder().score(10).build(), MovieReview.builder().score(5).build());
        return Movie.builder().name(STAR_WARS).awards(Arrays.asList(getAward(), getAward()))
                .userFavourites(userFavourites).userWatched(userWatched).reviews(reviews).build();
    }

    private Movie getIndianaJones() {
        Set<User> userFavourites = new HashSet<>(Arrays.asList(new User(), new User()));
        Set<User> userWatched = new HashSet<>(Arrays.asList(new User(), new User()));
        List<MovieReview> reviews = Arrays.asList(MovieReview.builder().score(7).build(), MovieReview.builder().score(3).build());
        return Movie.builder().name(INDIANA_JONES).awards(Arrays.asList(getAward(), getAward()))
                .userFavourites(userFavourites).userWatched(userWatched).reviews(reviews).build();
    }

    private MovieAward getAward() { return MovieAward.builder().awardCategory(CATEGORY).country(COUNTRY).date(DATE).build(); }

    private MovieAwardDTO getAwardDTO() { return MovieAwardDTO.builder().awardCategory(CATEGORY).country(COUNTRY).date(DATE).build(); }


    @DisplayName("Happy Path, method = getAllMovies")
    @Test
    void getAllMovies() {
        List<Movie> movies = getMovies();

        when(movieRepository.findAll()).thenReturn(movies);

        List<MovieDTO> returnDTO = movieService.getAllMovies();

        assertEquals(returnDTO.size(), 3);
    }


    @DisplayName("Happy Path, method = getAllMoviesPaged")
    @Test
    void getAllMoviesPaged() {
        List<Movie> movies = getMovies();

        PageRequest pageable = PageRequest.of(0, 3);

        Page<Movie> moviePage = new PageImpl<>(movies, pageable, movies.size());

        when(moviePageRepository.findAll(pageable)).thenReturn(moviePage);

        Page<Movie> returnMovie = movieService.getAllMoviesPaged(pageable);

        assertEquals(returnMovie.getTotalElements(), 3);
        assertEquals(returnMovie.getTotalPages(), 1);
    }



    @DisplayName("Happy Path, method = getPageMovieDTO")
    @Test
    void getPageMovieDTO() {
        // Given
        List<Movie> movieList = Arrays.asList(new Movie(), new Movie(), new Movie());
        PageRequest pageable = PageRequest.of(0, 5);
        Page<Movie> moviePage = new PageImpl<>(movieList, pageable, movieList.size());

        Page<MovieDTO> returnDTO = movieService.getPageMovieDTO(moviePage, pageable);

        assertEquals(returnDTO.getTotalPages(), 1);
        assertEquals(returnDTO.getContent().size(), 3);
    }


    @DisplayName("Happy Path, method = getAllMoviesForCategoryPaged")
    @Test
    void getAllMoviesForCategoryPaged() {
        List<Movie> movies = getMovies();

        PageRequest pageable = PageRequest.of(0, 3);

        Page<Movie> moviePage = new PageImpl<>(movies, pageable, movies.size());

        when(moviePageRepository.findAllByCategory_Name("Sci-fi", pageable)).thenReturn(Optional.of(moviePage));

        Page<Movie> returnMovie = movieService.getAllMoviesForCategoryPaged("Sci-fi", pageable);

        assertEquals(returnMovie.getTotalElements(), 3);
        assertEquals(returnMovie.getTotalPages(), 1);
    }


    @DisplayName("Happy Path, method = getMovieByID")
    @Test
    void getMovieByIDHappyPath() {
        Movie movie = getStarWars();

        when(movieRepository.findById(anyLong())).thenReturn(Optional.of(movie));

        MovieDTO returnDTO = movieService.getMovieByID(1L);

        assertEquals(returnDTO.getName(), STAR_WARS);
    }


    @DisplayName("UnHappyPath, method = findAllMoviesWithActor")
    @Test
    void getMovieByIDUnHappyPath() {
        Throwable ex = catchThrowable(() -> movieService.getMovieByID(4442L));

        assertThat(ex).isInstanceOf(ResourceNotFoundException.class);
    }


    @DisplayName("Happy Path, method = getMovieByName")
    @Test
    void getMovieByNameHappyPath() {
        Movie movie = getStarWars();

        when(movieRepository.findByName(anyString())).thenReturn(Optional.of(movie));

        MovieDTO returnDTO = movieService.getMovieByName(STAR_WARS);

        assertEquals(returnDTO.getName(), STAR_WARS);
    }


    @DisplayName("UnHappyPath, method = getMovieByName")
    @Test
    void getMovieByNameUnHappyPath() {
        Throwable ex = catchThrowable(() -> movieService.getMovieByName("Breaking Bad"));

        assertThat(ex).isInstanceOf(ResourceNotFoundException.class);
    }


    @DisplayName("Happy Path, method = findAllMoviesWithActor")
    @Test
    void findAllMoviesWithActorHappyPath() {
        List<Movie> movies = getMovies();
        Actor actor = Actor.builder().firstName("Arnold").movies(movies).build();

        when(actorRepository.findByFirstNameAndAndLastName(anyString(), anyString())).thenReturn(Optional.of(actor));

        List<MovieDTO> returnDTO = movieService.findAllMoviesWithActor("Arnold", "Arnold");

        assertEquals(returnDTO.size(), 3);
    }


    @DisplayName("Happy Path, method = getMostFavouriteMovies")
    @Test
    void getMostFavouriteMovies() {
        List<Movie> movies = Arrays.asList(getStarWars(), getIndianaJones());

        when(movieRepository.findAll()).thenReturn(movies);

        List<MovieDTO> returnDTO = movieService.getMostFavouriteMovies();

        assertEquals(returnDTO.get(0).getName(), STAR_WARS);
        assertEquals(returnDTO.get(1).getName(), INDIANA_JONES);
        assertEquals(returnDTO.size(), 2);
    }


    @DisplayName("Happy Path, method = getMostWatchedMovies")
    @Test
    void getMostWatchedMovies() {
        List<Movie> movies = Arrays.asList(getStarWars(), getIndianaJones());

        when(movieRepository.findAll()).thenReturn(movies);

        List<MovieDTO> returnDTO = movieService.getMostWatchedMovies();

        assertEquals(returnDTO.get(0).getName(), STAR_WARS);
        assertEquals(returnDTO.get(1).getName(), INDIANA_JONES);
        assertEquals(returnDTO.size(), 2);
    }


    @DisplayName("Happy Path, method = getMoviesByRating")
    @Test
    void getMoviesByRatingHappyPath() {
        List<Movie> movies = Arrays.asList(getStarWars(), getIndianaJones());

        when(movieRepository.findAll()).thenReturn(movies);

        Map<Double, List<MovieDTO>> returnDTO = movieService.getMoviesByRating();

        assertEquals(returnDTO.size(), 2);
        assertThat(returnDTO.containsKey(7.5)); // (10 + 5) / 2 = 7.5
    }


    @DisplayName("UnHappy Path, method = getMoviesByRating")
    @Test
    void getMoviesByRatingUnHappyPath() {
        List<Movie> movies = Arrays.asList(new Movie(), new Movie());

        when(movieRepository.findAll()).thenReturn(movies);

        Throwable ex = catchThrowable(() -> movieService.getMoviesByRating());

        assertThat(ex).isInstanceOf(EmptyListException.class);
    }


    @DisplayName("UnHappyPath, method = findAllMoviesWithActor")
    @Test
    void findAllMoviesWithActorUnHappyPath() {
        Throwable ex = catchThrowable(() -> movieService.findAllMoviesWithActor("Spider", "Chum"));

        assertThat(ex).isInstanceOf(ResourceNotFoundException.class);
    }


    @DisplayName("Happy Path, method = createMovie")
    @Test
    void createMovie() {
        MovieDTO movieDTO = new MovieDTO();
        movieDTO.setDescription(GOOD_MOVIE);

        MovieDTO returnDTO = movieService.createMovie(movieDTO);

        verify(movieRepository, times(1)).save(any(Movie.class));

        assertEquals(returnDTO.getDescription(), GOOD_MOVIE);
    }


    @DisplayName("Happy Path, method = addCategoryToMovie")
    @Test
    void addCategoryToMovie() {
        Movie movie = new Movie();
        movie.setDescription(GOOD_MOVIE);
        Category category = new Category();

        when(movieRepository.findById(anyLong())).thenReturn(Optional.of(movie));
        when(categoryRepository.findById(anyLong())).thenReturn(Optional.of(category));

        MovieDTO returnDTO = movieService.addCategoryToMovie(1L, 1L);

        verify(movieRepository, times(1)).save(any(Movie.class));
        verify(categoryRepository, times(1)).save(any(Category.class));

        assertEquals(returnDTO.getDescription(), GOOD_MOVIE);
    }


    @DisplayName("Happy Path, method = addAward")
    @Test
    void addAwardHappyPath() {
       Movie movie = new Movie();
       MovieAwardDTO awardDTO = getAwardDTO();

       when(movieRepository.findById(anyLong())).thenReturn(Optional.of(movie));

       MovieAwardDTO returnDTO = movieService.addAward(1L, awardDTO);

       assertEquals(returnDTO.getAwardCategory(), CATEGORY);
       assertEquals(returnDTO.getCountry(), COUNTRY);
       assertEquals(returnDTO.getDate(), DATE);
    }


    @DisplayName("UnHappy Path, method = addAward")
    @Test
    void addAwardUnHappyPath() {
        MovieAwardDTO awardDTO = new MovieAwardDTO();
        Throwable ex = catchThrowable(() -> movieService.addAward(1L, awardDTO));

        assertThat(ex).isInstanceOf(ResourceNotFoundException.class);
    }


    @DisplayName("Happy Path, method = updateMovie")
    @Test
    void updateMovieHappyPath() {
        MovieDTO movieDTO = new MovieDTO();
        movieDTO.setDescription(GOOD_MOVIE);
        Movie movie = getStarWars();

        when(movieRepository.findById(anyLong())).thenReturn(Optional.of(movie));

        MovieDTO returnDTO = movieService.updateMovie(1L, movieDTO);

        verify(movieRepository, times(1)).save(any(Movie.class));

        assertEquals(returnDTO.getDescription(), GOOD_MOVIE);
    }


    @DisplayName("UnHappy Path, method = updateMovie")
    @Test
    void updateMovieUnHappyPath() {
        MovieDTO movieDTO = new MovieDTO();

        Throwable ex = catchThrowable(() -> movieService.updateMovie(1L, movieDTO));

        assertThat(ex).isInstanceOf(ResourceNotFoundException.class);
    }

    
    @DisplayName("Happy Path, method = updateAward")
    @Test
    void updateAwardHappyPath() {
        Movie movie = new Movie();
        MovieAward award = new MovieAward();
        award.setId(1L);
        movie.getAwards().add(award);
        award.setMovie(movie);
        MovieAwardDTO awardDTO = new MovieAwardDTO();
        awardDTO.setCountry(COUNTRY);

        when(movieRepository.findById(anyLong())).thenReturn(Optional.of(movie));

        MovieAwardDTO returnDTO = movieService.updateAward(1L, 1L, awardDTO);

        assertEquals(returnDTO.getCountry(), COUNTRY);
    }


    @DisplayName("UnHappy Path, method = updateAward")
    @Test
    void updateAwardUnHappyPath() {
        MovieAwardDTO awardDTO = new MovieAwardDTO();
        Throwable ex = catchThrowable(() -> movieService.updateAward(1L, 1L, awardDTO));

        assertThat(ex).isInstanceOf(ResourceNotFoundException.class);
    }


    @DisplayName("Happy Path, method = updateMovieFields")
    @Test
    void updateMovieFieldsHappyPath() {
        Movie movie = getStarWars();
        MovieDTO movieDTO = MovieDTO.builder().name("Test").description(TESTING_DESCRIPTION).build();

        when(movieRepository.findById(anyLong())).thenReturn(Optional.of(movie));

        MovieDTO returnDTO = movieService.updateMovieFields(1L, movieDTO);

        assertEquals(returnDTO.getDescription(), TESTING_DESCRIPTION);
        assertEquals(returnDTO.getName(), "Test");
        assertEquals(returnDTO.getUserFavouritesDTO().size(), 4);
        assertEquals(returnDTO.getUserWatchedDTO().size(), 4);

        verify(movieRepository, times(1)).save(any(Movie.class));
    }



    @DisplayName("Happy Path, method = deleteMovieByID")
    @Test
    void deleteMovieByIDHappyPath() {
        Movie movie = new Movie();
        Category category = new Category();
        category.getMovies().add(movie);
        movie.setCategory(category);

        when(movieRepository.findById(anyLong())).thenReturn(Optional.of(movie));

        movieService.deleteMovieByID(1L);

        verify(movieRepository, times(1)).deleteById(1L);
    }


    @DisplayName("UnHappy Path, method = deleteMovieByID")
    @Test
    void deleteMovieByIDUnHappyPath() {
        Throwable ex = catchThrowable(() -> movieService.deleteMovieByID(1L));

        assertThat(ex).isInstanceOf(ResourceNotFoundException.class);
    }


    @DisplayName("Happy Path, method = deleteAwardByID")
    @Test
    void deleteAwardByIDHappyPath() {
        Movie movie = new Movie();
        MovieAward award = new MovieAward();
        award.setId(1L);
        movie.getAwards().add(award);
        award.setMovie(movie);

        when(movieRepository.findById(anyLong())).thenReturn(Optional.of(movie));

        movieService.deleteAwardByID(1L, 1L);

        verify(awardRepository, times(1)).deleteById(1L);
    }


    @DisplayName("UnHappy Path, method = deleteAwardByID")
    @Test
    void deleteAwardByIDUnHappyPath() {
        Throwable ex = catchThrowable(() -> movieService.deleteAwardByID(1L, 1L));

        assertThat(ex).isInstanceOf(ResourceNotFoundException.class);
    }

}
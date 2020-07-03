package adrianromanski.movies.services.movie;

import adrianromanski.movies.domain.award.MovieAward;
import adrianromanski.movies.domain.base_entity.Movie;
import adrianromanski.movies.domain.person.Actor;
import adrianromanski.movies.exceptions.ResourceNotFoundException;
import adrianromanski.movies.jms.JmsTextMessageService;
import adrianromanski.movies.mapper.award.MovieAwardMapper;
import adrianromanski.movies.mapper.base_entity.MovieMapper;
import adrianromanski.movies.model.award.MovieAwardDTO;
import adrianromanski.movies.model.base_entity.MovieDTO;
import adrianromanski.movies.repositories.award.AwardRepository;
import adrianromanski.movies.repositories.base_entity.MovieRepository;
import adrianromanski.movies.repositories.person.ActorRepository;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
public class MovieServiceImpl implements MovieService {

    private final MovieRepository movieRepository;
    private final ActorRepository actorRepository;
    private final AwardRepository awardRepository;
    private final JmsTextMessageService jmsTextMessageService;
    private final MovieMapper movieMapper;
    private final MovieAwardMapper awardMapper;


    public MovieServiceImpl(MovieRepository movieRepository, ActorRepository actorRepository, AwardRepository awardRepository,
                            JmsTextMessageService jmsTextMessageService, MovieMapper movieMapper, MovieAwardMapper awardMapper) {
        this.movieRepository = movieRepository;
        this.actorRepository = actorRepository;
        this.awardRepository = awardRepository;
        this.jmsTextMessageService = jmsTextMessageService;
        this.movieMapper = movieMapper;
        this.awardMapper = awardMapper;
    }

    /**
     * @return All Movies from database
     */
    @Override
    public List<MovieDTO> getAllMovies() {
        jmsTextMessageService.sendTextMessage("Listing Movies");
        return movieRepository.findAll()
                .stream()
                .map(movieMapper::movieToMovieDTO)
                .collect(toList());
    }


    /**
     * @param id of the movie
     * @return Movie with matching id
     * @throws ResourceNotFoundException if not found
     */
    @Override
    public MovieDTO getMovieByID(Long id) {
        jmsTextMessageService.sendTextMessage("Finding movie with id: " + id);
        return movieRepository.findById(id)
                .map(movieMapper::movieToMovieDTO)
                .orElseThrow(() -> new ResourceNotFoundException(id, Movie.class));
    }


    /**
     * @param name of the movie
     * @return Movie with matching name
     * @throws ResourceNotFoundException if not found
     */
    @Override
    public MovieDTO getMovieByName(String name) {
        jmsTextMessageService.sendTextMessage("Finding movie with name: " + name);
        return movieRepository.findByName(name)
                .map(movieMapper::movieToMovieDTO)
                .orElseThrow(() -> new ResourceNotFoundException(name, Movie.class));
    }


    /**
     * @param firstName of Actor
     * @param lastName of Actor
     * @return list of Movies with that Actor
     * @throws ResourceNotFoundException if not found
     */
    @Override
    public List<MovieDTO> findAllMoviesWithActor(String firstName, String lastName) {
        jmsTextMessageService.sendTextMessage("Finding movies for Actor:" + firstName + " " + lastName);
        Actor actor = actorRepository.findByFirstNameAndAndLastName(firstName, lastName)
                .orElseThrow(() -> new ResourceNotFoundException(firstName, lastName, Actor.class));
        jmsTextMessageService.sendTextMessage("Founded " + actor.getMovies().size()  + " movies");
        return actor.getMovies()
                .stream()
                .map(movieMapper::movieToMovieDTO)
                .collect(toList());
    }


    /**
     * @param movieDTO to save
     * @return Movie if successfully saved
     */
    @Override
    public MovieDTO createMovie(MovieDTO movieDTO) {
        Movie movie = movieMapper.movieDTOToMovie(movieDTO);
        movieRepository.save(movie);
        jmsTextMessageService.sendTextMessage("Movie " + movie.getName() + " successfully saved");
        return movieMapper.movieToMovieDTO(movie);
    }


    /**
     * @param id of the Movie to add Award
     * @param awardDTO object for adding
     * @return Award if successfully added Award to Movie
     * @throws ResourceNotFoundException if Movie not found
     */
    @Override
    public MovieAwardDTO addAward(Long id, MovieAwardDTO awardDTO) {
        jmsTextMessageService.sendTextMessage("Adding award to Movie with id: " + id);
        Movie movie = movieRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(id, Movie.class));
        MovieAward award = awardMapper.awardDTOToAward(awardDTO);
        movie.getAwards().add(award);
        award.setMovie(movie);
        movieRepository.save(movie);
        awardRepository.save(award);
        jmsTextMessageService.sendTextMessage("Award successfully added to Movie with id: " + id);
        return awardMapper.awardToAwardDTO(award);
    }


    /**
      * @param id of the Movie to update
      * @param movieDTO object for updating
      * @return Movie if successfully updated
      * @throws ResourceNotFoundException if not found
      */
     @Override
       public MovieDTO updateMovie(Long id, MovieDTO movieDTO) {
       movieRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(id, Movie.class));
       Movie updatedMovie = movieMapper.movieDTOToMovie(movieDTO);
       updatedMovie.setId(id);
       movieRepository.save(updatedMovie);
       jmsTextMessageService.sendTextMessage("Movie " + updatedMovie.getName() + " successfully updated");
       return movieMapper.movieToMovieDTO(updatedMovie);
    }


    /**
     * @param movieID of The Movie we want to update Award
     * @param awardID of the Award we want to update
     * @param awardDTO Object with updated fields
     * @return Award if successfully updated
     * @throws ResourceNotFoundException if Movie or Award not found
     */
    @Override
    public MovieAwardDTO updateAward(Long movieID, Long awardID, MovieAwardDTO awardDTO) {
        jmsTextMessageService.sendTextMessage("Updating Award with id: " + awardID + " of Movie with id: " + movieID);
        Movie movie = movieRepository.findById(movieID)
                .orElseThrow(() -> new ResourceNotFoundException(movieID, Movie.class));
        MovieAward award = movie.getAwardOptional(awardID)
                .orElseThrow(() -> new ResourceNotFoundException(awardID, MovieAward.class));
        movie.getAwards().remove(award);
        MovieAward updatedAward = awardMapper.awardDTOToAward(awardDTO);
        updatedAward.setId(awardID);
        movie.getAwards().add(updatedAward);
        updatedAward.setMovie(movie);
        jmsTextMessageService.sendTextMessage("Award with id: " + awardID + " of Movie with id: " + movieID + " successfully updated");
        return awardMapper.awardToAwardDTO(updatedAward);
    }


    /**
     * @param id of the Movie to delete
     * @throws ResourceNotFoundException if not found
     */
    @Override
    public void deleteMovieByID(Long id) {
        jmsTextMessageService.sendTextMessage("Deleting Movie with id: " + id);
        movieRepository.findById(id)
           .orElseThrow(() -> new ResourceNotFoundException(id, Movie.class));
        movieRepository.deleteById(id);
        jmsTextMessageService.sendTextMessage("Movie with id: " + id + " successfully deleted");
    }


    /**
     * @param movieID of The movie we want to delete Award
     * @param awardID of the Award we want to delete
     * @throws ResourceNotFoundException if either Award or Movie not found
     */
    @Override
    public void deleteAwardByID(Long movieID, Long awardID) {
        jmsTextMessageService.sendTextMessage("Deleting Award with id: " + awardID + " of Movie with id: " + movieID);
        Movie movie = movieRepository.findById(movieID)
                .orElseThrow(() -> new ResourceNotFoundException(movieID, Movie.class));
        MovieAward award = movie.getAwardOptional(awardID)
                .orElseThrow(() -> new ResourceNotFoundException(awardID, MovieAward.class));
        movie.getAwards().remove(award);
        movieRepository.save(movie);
        awardRepository.deleteById(awardID);
        jmsTextMessageService.sendTextMessage("Award with id: " + awardID + " successfully deleted");
    }


}

package adrianromanski.movies.services.movie;

import adrianromanski.movies.domain.Actor;
import adrianromanski.movies.domain.Movie;
import adrianromanski.movies.exceptions.ResourceNotFoundException;
import adrianromanski.movies.jms.JmsTextMessageService;
import adrianromanski.movies.mapper.ActorMapper;
import adrianromanski.movies.mapper.MovieMapper;
import adrianromanski.movies.model.ActorDTO;
import adrianromanski.movies.model.MovieDTO;
import adrianromanski.movies.repositories.ActorRepository;
import adrianromanski.movies.repositories.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.*;

@Service
public class MovieServiceImpl implements MovieService {

    private final MovieRepository movieRepository;
    private final ActorRepository actorRepository;
    private final JmsTextMessageService jmsTextMessageService;
    private final MovieMapper movieMapper;


    @Autowired
    public MovieServiceImpl(MovieRepository movieRepository, ActorRepository actorRepository,
                            JmsTextMessageService jmsTextMessageService, MovieMapper movieMapper) {
        this.movieRepository = movieRepository;
        this.actorRepository = actorRepository;
        this.jmsTextMessageService = jmsTextMessageService;
        this.movieMapper = movieMapper;
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
}

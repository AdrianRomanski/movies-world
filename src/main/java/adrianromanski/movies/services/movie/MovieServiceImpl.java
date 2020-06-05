package adrianromanski.movies.services.movie;

import adrianromanski.movies.jms.JmsTextMessageService;
import adrianromanski.movies.mapper.MovieMapper;
import adrianromanski.movies.model.MovieDTO;
import adrianromanski.movies.repositories.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MovieServiceImpl implements MovieService {

    private final MovieRepository movieRepository;
    private final JmsTextMessageService jmsTextMessageService;
    private final MovieMapper movieMapper;


    @Autowired
    public MovieServiceImpl(MovieRepository movieRepository, JmsTextMessageService jmsTextMessageService, MovieMapper movieMapper) {
        this.movieRepository = movieRepository;
        this.jmsTextMessageService = jmsTextMessageService;
        this.movieMapper = movieMapper;
    }

    @Override
    public List<MovieDTO> findAll() {
        jmsTextMessageService.sendTextMessage("Listing Movies");
        return movieRepository.findAll()
                .stream()
                .map(movieMapper::movieToMovieDTO)
                .collect(Collectors.toList());
    }
}

package adrianromanski.movies.services.movie;

import adrianromanski.movies.aspects.creation_log.LogCreation;
import adrianromanski.movies.aspects.delete_log.LogDelete;
import adrianromanski.movies.aspects.paging_log.LogPaging;
import adrianromanski.movies.aspects.update_log.LogUpdate;
import adrianromanski.movies.domain.award.MovieAward;
import adrianromanski.movies.domain.base_entity.Category;
import adrianromanski.movies.domain.base_entity.Movie;
import adrianromanski.movies.domain.person.Actor;
import adrianromanski.movies.exceptions.ResourceNotFoundException;
import adrianromanski.movies.mapper.award.MovieAwardMapper;
import adrianromanski.movies.mapper.base_entity.MovieMapper;
import adrianromanski.movies.model.award.MovieAwardDTO;
import adrianromanski.movies.model.base_entity.MovieDTO;
import adrianromanski.movies.model.person.ActorDTO;
import adrianromanski.movies.repositories.award.AwardRepository;
import adrianromanski.movies.repositories.base_entity.CategoryRepository;
import adrianromanski.movies.repositories.base_entity.MovieRepository;
import adrianromanski.movies.repositories.pages.MoviePageRepository;
import adrianromanski.movies.repositories.person.ActorRepository;
import lombok.AllArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.Collections.reverseOrder;
import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;

@Log
@Service
@AllArgsConstructor
public class MovieServiceImpl implements MovieService {

    private final MovieRepository movieRepository;
    private final MoviePageRepository moviePageRepository;
    private final CategoryRepository categoryRepository;
    private final ActorRepository actorRepository;
    private final AwardRepository awardRepository;
    private final MovieMapper movieMapper;
    private final MovieAwardMapper awardMapper;


    /**
     * @return All Movies from database
     */
    @Override
    public List<MovieDTO> getAllMovies() {
        return movieRepository.findAll()
                .stream()
                .map(movieMapper::movieToMovieDTO)
                .collect(toList());
    }


    /**
     * @return All Movies from database Paged
     */
    @Override
    @LogPaging
    public Page<Movie> getAllMoviesPaged(Pageable pageable) {
        return moviePageRepository.findAll(pageable);
    }


    /**
     * Converting All Movies in Page Object to MovieDTO
     */
    @Override
    public Page<MovieDTO> getPageMovieDTO(Page<Movie> moviePage, Pageable pageable) {
        List<MovieDTO> moviesDTO = moviePage.get()
                .map(movieMapper::movieToMovieDTO)
                .collect(Collectors.toList());
        return new PageImpl<>(moviesDTO, pageable, moviesDTO.size());
    }


    /**
     * @return All Movies for Category
     */
    @Override
    public Page<Movie> getAllMoviesForCategoryPaged(String name, Pageable pageable) {
        return moviePageRepository.findAllByCategory_Name(name, pageable)
                .orElseThrow(() -> new ResourceNotFoundException(name, Category.class));
    }


    /**
     * @param id of the movie
     * @return Movie with matching id
     * @throws ResourceNotFoundException if not found
     */
    @Override
    public MovieDTO getMovieByID(Long id) {
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
        return movieRepository.findByName(name)
                .map(movieMapper::movieToMovieDTO)
                .orElseThrow(() -> new ResourceNotFoundException(name, Movie.class));
    }



    /**
     * @param id of Actor
     * @return list of Movies with that Actor
     * @throws ResourceNotFoundException if not found
     */
    @Override
    public List<MovieDTO> findAllMoviesWithActor(Long id) {
        Actor actor = actorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(id, Actor.class));
        return actor.getMovies()
                .stream()
                .map(movieMapper::movieToMovieDTO)
                .collect(toList());
    }


    /**
     * @return Return Actors for Movie
     */
    @Override
    public List<ActorDTO> findAllActorsForMovie(Long movieID) {
        MovieDTO movieDTO = movieRepository.findById(movieID)
                .map(movieMapper::movieToMovieDTO)
                .orElseThrow(() -> new ResourceNotFoundException(movieID, Movie.class));
        return movieDTO.getActorsDTO();
    }



    /**
     * @return List containing Most Favourite User Movies - LIMIT 100
     */
    @Override
    public List<MovieDTO> getMostFavouriteMovies() {
        return movieRepository.findAll()
                .stream()
                .sorted(comparing(m -> m.getUserFavourites().size(), reverseOrder()))
                .map(movieMapper::movieToMovieDTO)
                .limit(100)
                .collect(toList());
    }


    /**
     * @return List containing Most Watched User Movies - LIMIT 100
     */
    @Override
    public List<MovieDTO> getMostWatchedMovies() {
        return movieRepository.findAll()
                .stream()
                .sorted(comparing(m -> m.getUserWatched().size(), reverseOrder()))
                .map(movieMapper::movieToMovieDTO)
                .limit(100)
                .collect(toList());
    }


    /**
     * @return List containing Movies with highest rating ratio
     */
    @Override
    public Map<Double, List<MovieDTO>> getMoviesByRating() {
        return movieRepository.findAll()
                .stream()
                .map(movieMapper::movieToMovieDTO)
                .collect(groupingBy(MovieDTO::getAvgRating));
    }



    /**
     * @param movieDTO to save
     * @return Movie if successfully saved
     */
    @Override
    @LogCreation
    public MovieDTO createMovie(MovieDTO movieDTO) {
        Movie movie = movieMapper.movieDTOToMovie(movieDTO);
        movieRepository.save(movie);
        return movieMapper.movieToMovieDTO(movie);
    }


    @Override
    public MovieDTO addCategoryToMovie(Long movieID, Long categoryID) {
        Movie movie = movieRepository.findById(movieID)
                .orElseThrow(() -> new ResourceNotFoundException(movieID, Movie.class));
        Category category = categoryRepository.findById(categoryID)
                .orElseThrow(() -> new ResourceNotFoundException(movieID, Movie.class));
        movie.setCategory(category);
        category.getMovies().add(movie);
        movieRepository.save(movie);
        categoryRepository.save(category);
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
        Movie movie = movieRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(id, Movie.class));
        MovieAward award = awardMapper.awardDTOToAward(awardDTO);
        movie.getAwards().add(award);
        award.setMovie(movie);
        movieRepository.save(movie);
        awardRepository.save(award);
        return awardMapper.awardToAwardDTO(award);
    }


    /**
     *  Post operation
      * @param id of the Movie to update
      * @param movieDTO object for updating
      * @return Movie if successfully updated
      * @throws ResourceNotFoundException if not found
      */
     @Override
     @LogUpdate
       public MovieDTO updateMovie(Long id, MovieDTO movieDTO) {
       movieRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(id, Movie.class));
       Movie updatedMovie = movieMapper.movieDTOToMovie(movieDTO);
       updatedMovie.setId(id);
       movieRepository.save(updatedMovie);
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
        Movie movie = movieRepository.findById(movieID)
                .orElseThrow(() -> new ResourceNotFoundException(movieID, Movie.class));
        MovieAward award = movie.getAwardOptional(awardID)
                .orElseThrow(() -> new ResourceNotFoundException(awardID, MovieAward.class));
        movie.getAwards().remove(award);
        MovieAward updatedAward = awardMapper.awardDTOToAward(awardDTO);
        updatedAward.setId(awardID);
        movie.getAwards().add(updatedAward);
        updatedAward.setMovie(movie);
        return awardMapper.awardToAwardDTO(updatedAward);
    }



    /**
     * PATCH operation it only updates basic fields of movie like description,name,time
     * @param id of the Movie to update
     * @param movieDTO object for updating
     * @return Movie if successfully updated
     * @throws ResourceNotFoundException if not found
     */
    @Override
    @LogUpdate
    public MovieDTO updateMovieFields(Long id, MovieDTO movieDTO) {
        Movie movie = movieRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(id, Movie.class));
        movie.setDescription(movieDTO.getDescription());
        movie.setName(movieDTO.getName());
        movieRepository.save(movie);
        return movieMapper.movieToMovieDTO(movie);
    }


    /**
     * @param id of the Movie to delete
     * @throws ResourceNotFoundException if not found
     */
    @Override
    @LogDelete
    public void deleteMovieByID(Long id) {
        Movie movie = movieRepository.findById(id)
           .orElseThrow(() -> new ResourceNotFoundException(id, Movie.class));
        movie.getActors()
                .forEach(actor -> actor.getMovies().remove(movie));
        Category category = movie.getCategory();
        category.getMovies().remove(movie);
        movieRepository.deleteById(id);
        categoryRepository.save(category);
    }


    /**
     * @param movieID of The movie we want to delete Award
     * @param awardID of the Award we want to delete
     * @throws ResourceNotFoundException if either Award or Movie not found
     */
    @LogDelete
    @Override
    public void deleteAwardByID(Long movieID, Long awardID) {
        Movie movie = movieRepository.findById(movieID)
                .orElseThrow(() -> new ResourceNotFoundException(movieID, Movie.class));
        MovieAward award = movie.getAwardOptional(awardID)
                .orElseThrow(() -> new ResourceNotFoundException(awardID, MovieAward.class));
        movie.getAwards().remove(award);
        movieRepository.save(movie);
        awardRepository.deleteById(awardID);
    }

}

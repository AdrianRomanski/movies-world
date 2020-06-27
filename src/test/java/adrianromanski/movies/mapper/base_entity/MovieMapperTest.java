package adrianromanski.movies.mapper.base_entity;

import adrianromanski.movies.domain.base_entity.Movie;
import adrianromanski.movies.model.base_entity.MovieDTO;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MovieMapperTest {

    public static final String NAME = "Star Wars";
    public static final String DESCRIPTION = "A long time ago in a galaxy far, far away....";
    public static final String IMAGE_URL = "/pictures/starwars";
    public static final long ID = 1L;
    MovieMapper movieMapper = new MovieMapperImpl();

    @Test
    void movieToMovieDTO() {
        Movie movie = Movie.builder().name(NAME).description(DESCRIPTION).imageURL(IMAGE_URL).id(ID).build();

        MovieDTO movieDTO = movieMapper.movieToMovieDTO(movie);

        assertEquals(movieDTO.getName(), NAME);
        assertEquals(movieDTO.getDescription(), DESCRIPTION);
        assertEquals(movieDTO.getImageURL(), IMAGE_URL);
        assertEquals(movieDTO.getId(), ID);
    }

    @Test
    void movieDTOToMovie() {
        MovieDTO movieDTO = MovieDTO.builder().name(NAME).description(DESCRIPTION).imageURL(IMAGE_URL).id(ID).build();

        Movie movie = movieMapper.movieDTOToMovie(movieDTO);

        assertEquals(movie.getName(), NAME);
        assertEquals(movie.getDescription(), DESCRIPTION);
        assertEquals(movie.getImageURL(), IMAGE_URL);
        assertEquals(movie.getId(), ID);
    }
}
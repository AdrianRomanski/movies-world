package adrianromanski.movies.services.image;

import adrianromanski.movies.mapper.base_entity.MovieMapper;
import adrianromanski.movies.model.base_entity.MovieDTO;
import adrianromanski.movies.repositories.base_entity.MovieRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class ImageServiceMovie implements ImageService<MovieDTO> {

    private final MovieRepository movieRepository;
    private final MovieMapper movieMapper;

    public ImageServiceMovie(MovieRepository movieRepository, MovieMapper movieMapper) {
        this.movieRepository = movieRepository;
        this.movieMapper = movieMapper;
    }

    @Override
    public void saveImageFile(MovieDTO movieDTO, MultipartFile file) throws IOException {
        var movie = movieMapper.movieDTOToMovie(movieDTO);
        var byteObjects = new Byte[file.getBytes().length];

        var i = 0;

        for(byte b: file.getBytes()) {
            byteObjects[i++] = b;
        }
        movie.setImage(byteObjects);
        movieRepository.save(movie);
    }
}

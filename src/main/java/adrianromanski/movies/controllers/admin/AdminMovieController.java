package adrianromanski.movies.controllers.admin;

import adrianromanski.movies.mapper.base_entity.MovieMapper;
import adrianromanski.movies.repositories.base_entity.MovieRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminMovieController {

    private final MovieRepository movieRepository;
    private final MovieMapper movieMapper;

    public AdminMovieController(MovieRepository movieRepository, MovieMapper movieMapper) {
        this.movieRepository = movieRepository;
        this.movieMapper = movieMapper;
    }


    @GetMapping("/admin/showMovies")
    public String showMovies(Model model) {
        model.addAttribute("movies", movieRepository.findAll());
        return "admin/movie/showMoviesForm";
    }
}

package adrianromanski.movies.controllers.user;

import adrianromanski.movies.domain.base_entity.Movie;
import adrianromanski.movies.model.base_entity.MovieDTO;
import adrianromanski.movies.services.movie.MovieServiceImpl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
public class MoviesController {

    private final MovieServiceImpl movieService;

    public MoviesController(MovieServiceImpl movieService) {
        this.movieService = movieService;
    }


    @GetMapping("/movies/page/{page}")
    private ModelAndView showMovies(@PathVariable int page) {
        ModelAndView modelAndView = new ModelAndView("user/movies");
        PageRequest pageable = PageRequest.of(page - 1, 8);
        Page<Movie> moviePage = movieService.getAllMoviesPaged(pageable);
        Page<MovieDTO> movieDTOPage = movieService.getPageMovieDTO(moviePage, pageable);
        int totalPages = moviePage.getTotalPages();
        if(totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1,totalPages).boxed().collect(Collectors.toList());
            modelAndView.addObject("pageNumbers", pageNumbers);
        }
        modelAndView.addObject("activeMoviesList", true);
        modelAndView.addObject("moviesDTOList", movieDTOPage.getContent());
        return modelAndView;

    }
}

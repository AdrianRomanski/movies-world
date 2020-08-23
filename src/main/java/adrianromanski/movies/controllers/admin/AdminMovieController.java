package adrianromanski.movies.controllers.admin;

import adrianromanski.movies.mapper.base_entity.MovieMapper;
import adrianromanski.movies.model.base_entity.MovieDTO;
import adrianromanski.movies.services.category.CategoryService;
import adrianromanski.movies.services.movie.MovieService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Slf4j
@Controller
public class AdminMovieController {

    private final MovieService movieService;
    private final CategoryService categoryService;
    private final MovieMapper movieMapper;

    public AdminMovieController(MovieService movieService, CategoryService categoryService, MovieMapper movieMapper) {
        this.movieService = movieService;
        this.categoryService = categoryService;
        this.movieMapper = movieMapper;
    }


    @GetMapping("/admin/showMovies")
    public String showMovies(Model model) {
        model.addAttribute("movies", movieService.getAllMovies());
        return "admin/movie/showMoviesForm";
    }

    @GetMapping("/admin/createMovie")
    public String createMovie(Model model) {
        model.addAttribute("movie", new MovieDTO());
        return "admin/movie/createMovieForm";
    }

    @PostMapping("/admin/createMovie/check")
    public String checkMovieCreation(@Valid @ModelAttribute("movie") MovieDTO movieDTO,
                                     BindingResult bindingResult,
                                     Model model) {
        if(bindingResult.hasErrors()) {
            bindingResult.getAllErrors()
                    .forEach(objectError -> log.warn(objectError.getDefaultMessage()));
            return "admin/movie/createMovieForm";
        }
        MovieDTO movieDTOSaved = movieService.createMovie(movieDTO);
        model.addAttribute("categories",  categoryService.getAllCategories());
        model.addAttribute("newMovie", movieDTOSaved);
        return "admin/movie/selectCategoryForMovieForm";
    }


    @GetMapping("/admin/createMovie-{movieID}/addCategory-{categoryID}")
    public String addCategoryToMovie(@PathVariable String movieID, @PathVariable String categoryID,
                                     Model model) {
        movieService.addCategoryToMovie(Long.valueOf(movieID), Long.valueOf(categoryID));
        model.addAttribute("movies", movieService.getAllMovies());
        model.addAttribute("movie", movieService.getMovieByID(Long.valueOf(movieID)));
        return "admin/movie/movieImageUplForm";
    }


    @GetMapping("/admin/createMovie-{movieID}/addImage")
    public String addImageToMovie(@PathVariable String movieID, Model model) {
        model.addAttribute("movie", movieService.getMovieByID(Long.valueOf(movieID)));
        return "admin/movie/showMoviesForm";
    }


    @GetMapping("/admin/updateMovie/{id}")
    public String updateMovie(@PathVariable String id, Model model) {
        model.addAttribute("movie", movieService.getMovieByID(Long.valueOf(id)));
        return "admin/movie/updateMovieForm";
    }


    @PostMapping("admin/updateMovie/check")
    public String checkUpdateMovie(@Valid @ModelAttribute("movie") MovieDTO movieDTO,
                                      BindingResult bindingResult,
                                      Model model)  {
        if(bindingResult.hasErrors()){
            bindingResult.getAllErrors().forEach(objectError -> {
                log.warn(objectError.getDefaultMessage());
            });
            return "admin/movie/updateMovieForm";
        }
        movieService.updateMovieFields(movieDTO.getId(), movieDTO);
        model.addAttribute("movies", movieService.getAllMovies());
        return "admin/movie/showMoviesForm";
    }

}

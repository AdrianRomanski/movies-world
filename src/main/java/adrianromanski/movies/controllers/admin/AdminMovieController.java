package adrianromanski.movies.controllers.admin;

import adrianromanski.movies.domain.base_entity.Movie;
import adrianromanski.movies.mapper.base_entity.MovieMapper;
import adrianromanski.movies.model.base_entity.MovieDTO;
import adrianromanski.movies.services.category.CategoryService;
import adrianromanski.movies.services.movie.MovieService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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


    @RequestMapping(value = "/admin/showMovies/page/{page}")
    public ModelAndView showMoviesPaged(@PathVariable("page") int page) {
        ModelAndView modelAndView = new ModelAndView("admin/movie/showMoviesForm");
        PageRequest pageable = PageRequest.of(page - 1, 5);
        Page<Movie> moviePage = movieService.getAllMoviesPaged(pageable);
        Page<MovieDTO> movieDTOPage = movieService.getPageMovieDTO(moviePage, pageable);
        int totalPages = moviePage.getTotalPages();
        if(totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1,totalPages).boxed().collect(Collectors.toList());
            modelAndView.addObject("pageNumbers", pageNumbers);
        }
        modelAndView.addObject("activeMovieList", true);
        modelAndView.addObject("moviesDTOList", movieDTOPage.getContent());
        return modelAndView;
    }

    @GetMapping("/admin/createMovie")
    public String createMovie(Model model) {
        model.addAttribute("movieDTO", new MovieDTO());
        return "admin/movie/createMovieForm";
    }

    @PostMapping("/admin/createMovie/check")
    public String checkMovieCreation(@Valid @ModelAttribute("movieDTO") MovieDTO movieDTO,
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
        model.addAttribute("movieDTO", movieService.getMovieByID(Long.valueOf(movieID)));
        return "admin/movie/movieImageUplForm";
    }


    @GetMapping("/admin/updateMovie/{id}")
    public String updateMovie(@PathVariable String id, Model model) {
        model.addAttribute("movieDTO", movieService.getMovieByID(Long.valueOf(id)));
        return "admin/movie/updateMovieForm";
    }


    @PostMapping("/admin/updateMovie/check")
    public String checkUpdateMovie(@Valid @ModelAttribute("movieDTO") MovieDTO movieDTO,
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
        return "admin/adminHome"; // I'll change it later for something better
    }


    @GetMapping("/admin/deleteMovie/{id}")
    public String deleteMovie(@PathVariable String id, Model model) {
        movieService.deleteMovieByID(Long.valueOf(id));
        model.addAttribute("movies", movieService.getAllMovies());
        return "admin/adminHome"; // I'll change it later for something better
    }

}

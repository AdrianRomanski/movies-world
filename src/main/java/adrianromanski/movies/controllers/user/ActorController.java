package adrianromanski.movies.controllers.user;

import adrianromanski.movies.domain.person.Actor;
import adrianromanski.movies.services.actor.ActorServiceImpl;
import lombok.AllArgsConstructor;
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
@AllArgsConstructor
public class ActorController {

    private final ActorServiceImpl actorService;

    @GetMapping("/actor/{id}")
    public ModelAndView getActor(@PathVariable String id) {
        ModelAndView modelAndView = new ModelAndView("user/actors/showActors");
        modelAndView.addObject("actor", actorService.getActorByID(Long.valueOf(id)));
        return modelAndView;
    }


    @GetMapping("/actors/page/{page}")
    public ModelAndView getActors(@PathVariable int page) {
        ModelAndView modelAndView = new ModelAndView("user/actors/showActors");
        PageRequest pageable = PageRequest.of(page - 1, 8);
        return getModelAndView(modelAndView, pageable);
    }


//    @GetMapping("/actor/{id}/movies")
//    public ModelAndView getActorMovies(@PathVariable String id) {
//        ModelAndView modelAndView = new ModelAndView("user/actors/showActors");
//        modelAndView.addObject("movies", actorService.getAllMoviesForActor(Long.valueOf(id)));
//        return modelAndView;
//    }


    public ModelAndView getModelAndView(ModelAndView modelAndView, PageRequest pageable) {
        Page<Actor> actorPage = actorService.getActorsPaged(pageable);
        System.out.println(actorPage.getTotalElements());
//        Page<MovieDTO> movieDTOPage = movieService.getPageMovieDTO(moviePage, pageable);
        int totalPages = actorPage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages).boxed().collect(Collectors.toList());
            modelAndView.addObject("pageNumbers", pageNumbers);
        }
        modelAndView.addObject("activeActorsList", true);
        modelAndView.addObject("actorsList", actorPage.getContent());
        return modelAndView;
    }
}

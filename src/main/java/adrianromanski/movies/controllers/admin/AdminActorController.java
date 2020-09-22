package adrianromanski.movies.controllers.admin;

import adrianromanski.movies.domain.person.Actor;
import adrianromanski.movies.services.actor.ActorService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@AllArgsConstructor
public class AdminActorController {

    private final ActorService actorService;

    @RequestMapping(value = "/admin/actor/showActors/page/{page}")
    public ModelAndView showActorsPaged(@PathVariable("page") int page) {
        ModelAndView modelAndView = new ModelAndView("admin/actor/showActorsForm");
        PageRequest pageable = PageRequest.of(page - 1, 12);
        Page<Actor> actorPage = actorService.getActorsPaged(pageable);
        int totalPages = actorPage.getTotalPages();
        if(totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1,totalPages).boxed().collect(Collectors.toList());
            modelAndView.addObject("pageNumbers", pageNumbers);
        }
        modelAndView.addObject("activeActorsList", true);
        modelAndView.addObject("actorsList", actorPage.getContent());
        return modelAndView;
    }

}

package adrianromanski.movies.controllers.user;

import adrianromanski.movies.services.event.EventServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

@Controller
@AllArgsConstructor
public class EventController {

    private final EventServiceImpl eventService;


    @GetMapping("/event/{id}")
    public ModelAndView getEventById(@PathVariable String id) {
        ModelAndView modelAndView = new ModelAndView("/user/events/showEvent");
        modelAndView.addObject("event", eventService.getEventByID(Long.valueOf(id)));
        return modelAndView;
    }
}

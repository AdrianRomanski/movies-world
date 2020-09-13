package adrianromanski.movies.controllers.user;

import adrianromanski.movies.services.news.NewsServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@AllArgsConstructor
public class HomeController {

    private final NewsServiceImpl newsService;

    @GetMapping("/")
    public ModelAndView home() {
        ModelAndView modelAndView = new ModelAndView("user/home");
        modelAndView.addObject("latestNews", newsService.getLatestNews());
        return modelAndView;
    }



}

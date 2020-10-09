package adrianromanski.movies.controllers.admin;

import adrianromanski.movies.domain.base_entity.News;
import adrianromanski.movies.services.news.NewsService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@AllArgsConstructor
public class AdminNewsController {

    private final NewsService newsService;


    @GetMapping("/admin/news/showNews/page/{page}")
    public ModelAndView getNewsPaged(@PathVariable int page) {
        ModelAndView modelAndView = new ModelAndView("admin/news/showNewsForm");
        PageRequest pageable = PageRequest.of(page - 1, 12);
        Page<News> newPage = newsService.getNewsPaged(pageable);
        int totalPages = newPage.getTotalPages();
        if(totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1,totalPages).boxed().collect(Collectors.toList());
            modelAndView.addObject("pageNumbers", pageNumbers);
        }
        modelAndView.addObject("activeNewsList", true);
        modelAndView.addObject("newsList", newPage.getContent());
        return modelAndView;
    }


    @GetMapping("/admin/news/{id}")
    public String showSingleNews(@PathVariable String id, Model model) {
        model.addAttribute("news", newsService.getNewsByID(Long.valueOf(id)));
        return "admin/news/showSingleNews";
    }

}

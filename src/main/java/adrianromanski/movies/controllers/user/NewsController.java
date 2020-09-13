package adrianromanski.movies.controllers.user;

import adrianromanski.movies.domain.base_entity.News;
import adrianromanski.movies.services.news.NewsServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@AllArgsConstructor
public class NewsController {

    private final NewsServiceImpl newsService;


    @GetMapping("/news/{id}")
    public ModelAndView getEventById(@PathVariable String id) {
        ModelAndView modelAndView = new ModelAndView("user/news/showSingleNews");
        modelAndView.addObject("singleNews", newsService.getNewsByID(Long.valueOf(id)));
        return modelAndView;
    }


    @GetMapping("/news/sorted/{sortType}/{sortBy}/page/{page}")
    private ModelAndView showNewsSortedBy(@PathVariable int page,
                                            @PathVariable String sortBy, @PathVariable String sortType) {
        ModelAndView modelAndView = new ModelAndView("user/news/showAllNews");
        modelAndView.addObject("sortType", sortType);
        PageRequest pageable = null;
        if(sortType.equals("asc")) {
            pageable = PageRequest.of(page - 1, 5, Sort.by(sortBy).ascending());
        } else if(sortType.equals("desc")) {
            pageable = PageRequest.of(page - 1, 5, Sort.by(sortBy).descending());
        }
        return getModelAndView(modelAndView, pageable);
    }


    public ModelAndView getModelAndView(ModelAndView modelAndView, PageRequest pageable) {
        Page<News> newsPage = newsService.getNewsPaged(pageable);
        int totalPages = newsPage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages).boxed().collect(Collectors.toList());
            modelAndView.addObject("pageNumbers", pageNumbers);
        }
        modelAndView.addObject("activeNewsPage", true);
        modelAndView.addObject("newsList", newsPage.getContent());
        return modelAndView;
    }
}

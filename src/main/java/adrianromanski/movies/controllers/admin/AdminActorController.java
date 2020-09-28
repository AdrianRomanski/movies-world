package adrianromanski.movies.controllers.admin;

import adrianromanski.movies.domain.person.Actor;
import adrianromanski.movies.model.person.ActorDTO;
import adrianromanski.movies.services.actor.ActorService;
import lombok.AllArgsConstructor;
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

    @GetMapping("admin/actor/createActor")
    public String createActor(Model model) {
        model.addAttribute("actorDTO", new ActorDTO());
        return "admin/actor/createActorForm";
    }


    @PostMapping("/admin/actor/createActor/check")
    public String checkActorCreation(@Valid @ModelAttribute("actorDTO") ActorDTO actorDTO,
                                     BindingResult bindingResult, Model model) {
        if(bindingResult.hasErrors()) {
            bindingResult.getAllErrors()
                    .forEach(objectError -> log.warn(objectError.getDefaultMessage()));
            return "admin/actor/createActorForm";
        }
        ActorDTO savedActor = actorService.createActor(actorDTO);
        model.addAttribute("actorDTO", savedActor);
        return "admin/actor/actorImageUplForm";
    }


    @GetMapping("admin/actor/update/{id}")
    public String updateActor(Model model, @PathVariable String id) {
        model.addAttribute("actorDTO", actorService.getActorByID(Long.valueOf(id)));
        return "admin/actor/updateActorForm";
    }


    @PostMapping("admin/actor/update/check")
    public String checkActorUpdate(@Valid @ModelAttribute("actorDTO") ActorDTO actorDTO,
                                      BindingResult bindingResult)  {
        if(bindingResult.hasErrors()){
            bindingResult.getAllErrors().forEach(objectError -> {
                log.warn(objectError.getDefaultMessage());
            });
            return "admin/actor/updateActorForm";
        }
        actorService.updateActor(actorDTO.getId(), actorDTO);
        return "redirect:/admin/actor/showActors/page/1";
    }


    @GetMapping("admin/actor/delete/{id}")
    public String deleteActor(@PathVariable String id) {
        actorService.deleteActorByID(Long.valueOf(id));
        return "redirect:/admin/actor/showActors/page/1";
    }
}

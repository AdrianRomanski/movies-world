package adrianromanski.movies.controllers.registration;

import adrianromanski.movies.model.person.UserDTO;
import adrianromanski.movies.services.user.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Slf4j
@Controller
public class RegistrationController {

    private final UserService userService;

    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/register")
    public String registrationForm(Model model) {
        model.addAttribute("userDTO", new UserDTO());

        return "registration";
    }


    @PostMapping("/checkRegistration")
    public String checkRegistration(@Valid @ModelAttribute("userDTO") UserDTO userDTO, BindingResult bindingResult) {
        if(bindingResult.hasErrors()){
            bindingResult.getAllErrors().forEach(objectError -> {
                log.warn(objectError.getDefaultMessage());
            });
            return "registration";
        }

        userService.createUser(userDTO);
        return "successRegistration";
    }
}

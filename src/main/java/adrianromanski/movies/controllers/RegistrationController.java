package adrianromanski.movies.controllers;

import adrianromanski.movies.model.UserDTO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
public class RegistrationController {

    @RequestMapping("/registration")
    public String registrationForm(Model model) {

        model.addAttribute("userDTO", new UserDTO());

        return "registration";
    }

    @PostMapping("/checkRegistration")
    public String checkRegistration(@Valid UserDTO userDTO, BindingResult bindingResult) {

        if(bindingResult.hasErrors()) {
            return "registration";
        }

        return "successRegistration";
    }
}

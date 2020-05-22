package adrianromanski.movies.controllers;

import adrianromanski.movies.model.UserDTO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("movies-world")
public class LoginController {

    @RequestMapping("/login")
    public String showLoginForm(Model model){

        model.addAttribute("userDTO", new UserDTO());
        return "loginForm";
    }

    @RequestMapping("/logout")
    public String yourLoggedOut(){

        return "logout-success";
    }
}

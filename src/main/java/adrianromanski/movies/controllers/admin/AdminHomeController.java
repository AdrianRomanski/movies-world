package adrianromanski.movies.controllers.admin;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminHomeController {


    @GetMapping({"/admin/home", "/admin"})
    public String getAdminHome() {
        return "admin/adminHome";
    }
}

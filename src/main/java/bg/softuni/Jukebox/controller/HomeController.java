package bg.softuni.Jukebox.controller;

import bg.softuni.Jukebox.service.CurrentUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    private final CurrentUser currentUser;

    public HomeController(CurrentUser currentUser) {
        this.currentUser = currentUser;
    }

    @GetMapping("/")
    public String home() {
        if (currentUser.isLoggedIn()) {
            return "redirect:/home";
        }
        return "index";
    }

    @GetMapping("/home")
    public String home(Model model) {
//        if (!currentUser.isLoggedIn()) {
//            return "redirect:/";
//        }
        return "home";
    }
}

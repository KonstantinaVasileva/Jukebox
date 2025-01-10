package bg.softuni.Jukebox.controller;

import bg.softuni.Jukebox.service.CurrentUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
//@RequestMapping("/users")
public class UserController {

    private final CurrentUser currentUser;

    public UserController(CurrentUser currentUser) {
        this.currentUser = currentUser;
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/logout")
    public String logout() {
        currentUser.logout();
        return "redirect:/";
    }
}

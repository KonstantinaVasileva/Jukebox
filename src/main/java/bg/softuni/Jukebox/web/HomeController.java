package bg.softuni.Jukebox.web;

import bg.softuni.Jukebox.model.entity.User;
import bg.softuni.Jukebox.service.UserService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    private final UserService userService;

    public HomeController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public String getIndex() {

        return "index";
    }

    @GetMapping("/home")
    public String getHome(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        String name = userDetails.getUsername();
        User user = userService.findByUsername(name);
        model.addAttribute("user", user);
        return "home";
    }
}

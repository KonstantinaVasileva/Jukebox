package bg.softuni.Jukebox.controller;

import bg.softuni.Jukebox.model.dto.LoginDTO;
import bg.softuni.Jukebox.service.CurrentUser;
import bg.softuni.Jukebox.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
//@RequestMapping("/users")
public class UserController {

    private final CurrentUser currentUser;
    private final UserService userService;

    public UserController(CurrentUser currentUser, UserService userService) {
        this.currentUser = currentUser;
        this.userService = userService;
    }

    @ModelAttribute("loginUserDTO")
    public LoginDTO getLoginUserDTO() {
        return new LoginDTO();
    }

    @ModelAttribute("logonError")
    public void getLoginError(Model model) {
        model.addAttribute("loginError");
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

    @PostMapping("/login")
    public String login(LoginDTO loginDTO,
                        RedirectAttributes redirectAttributes) {

        Boolean login = userService.login(loginDTO.getUsername(), loginDTO.getPassword());
        if (!login) {
            redirectAttributes.addFlashAttribute("message", "Invalid username or password");
            redirectAttributes.addFlashAttribute("loginError", true);
            return "redirect:/login";
        }

        return "redirect:/home";
    }
}

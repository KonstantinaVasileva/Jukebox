package bg.softuni.Jukebox.controller;

import bg.softuni.Jukebox.model.dto.LoginDTO;
import bg.softuni.Jukebox.model.dto.RegisterUserDTO;
import bg.softuni.Jukebox.service.CurrentUser;
import bg.softuni.Jukebox.service.UserService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
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

    @ModelAttribute("registerUserDTO")
    public RegisterUserDTO registerUserDTO() {
        return new RegisterUserDTO();
    }

    @ModelAttribute("loginUserDTO")
    public LoginDTO getLoginUserDTO() {
        return new LoginDTO();
    }

    @ModelAttribute("logonError")
    public void getLoginError(Model model) {
        model.addAttribute("loginError");
    }

//    @GetMapping("/register")
//    public String register() {
//        if (currentUser.isLoggedIn()) {
//            return "redirect:/home";
//        }
//        return "register";
//    }

    @GetMapping("/register")
    public String register(Model model) {
        RegisterUserDTO registerDTO = new RegisterUserDTO();
        model.addAttribute("registerDTO", registerDTO);

        // Add this for testing
        BeanPropertyBindingResult errors = new BeanPropertyBindingResult(registerDTO, "registerDTO");
        errors.rejectValue("username", "error.username", "Test error message");
        model.addAttribute(BindingResult.MODEL_KEY_PREFIX + "registerDTO", errors);

        return "register";
    }

    @PostMapping("/register")
    public String register(@Valid RegisterUserDTO registerUserDTO,
                           BindingResult bindingResult,
                           RedirectAttributes redirectAttributes) {
        System.out.println("Errors: " + bindingResult.getAllErrors());

        if (registerUserDTO.getPassword()!=null &&
                !registerUserDTO.getPassword().equals(registerUserDTO.getRepeatPassword())) {
            bindingResult.addError(
                    new FieldError("registerUserDTO", "repeatPassword", "Passwords do not match")
            );
        }

        boolean isValid = userService.validateUserRegistration(registerUserDTO);
        if (bindingResult.hasErrors() || !isValid) {
            redirectAttributes.addFlashAttribute("registerUserDTO", registerUserDTO);
            redirectAttributes.addFlashAttribute(
                    "org.springframework.validation.BindingResult.registerUserDTO", bindingResult);
            return "register";
        }
        userService.register(registerUserDTO);
        return "redirect:/home";
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

        boolean login = userService.login(loginDTO.getUsername(), loginDTO.getPassword());
        if (!login) {
            redirectAttributes.addFlashAttribute("message", "Invalid username or password");
            redirectAttributes.addFlashAttribute("loginError", true);
            return "redirect:/login";
        }

        return "redirect:/home";
    }
}

package bg.softuni.Jukebox.controller;

import bg.softuni.Jukebox.model.dto.LoginUserRequest;
import bg.softuni.Jukebox.model.dto.RegisterUserRequest;
import bg.softuni.Jukebox.service.CurrentUser;
import bg.softuni.Jukebox.service.UserService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
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

    @ModelAttribute("registerUserRequest")
    public RegisterUserRequest registerUserRequest() {
        return new RegisterUserRequest();
    }

    @ModelAttribute("loginUserRequest")
    public LoginUserRequest getLoginUserRequest() {
        return new LoginUserRequest();
    }

    @ModelAttribute("logonError")
    public void getLoginError(Model model) {
        model.addAttribute("loginError");
    }

    @GetMapping("/register")
    public String register() {
        if (currentUser.isLoggedIn()) {
            return "redirect:/home";
        }
        return "register";
    }

//    @GetMapping("/register")
//    public String register(Model model) {
//        RegisterUserDTO registerDTO = new RegisterUserDTO();
//        model.addAttribute("registerDTO", registerDTO);
//
//        // Add this for testing
//        BeanPropertyBindingResult errors = new BeanPropertyBindingResult(registerDTO, "registerDTO");
//        errors.rejectValue("username", "error.username", "Test error message");
//        model.addAttribute(BindingResult.MODEL_KEY_PREFIX + "registerDTO", errors);
//
//        return "register";
//    }

    @PostMapping("/register")
    public String register(@Valid RegisterUserRequest registerUserRequest,
                           BindingResult bindingResult,
                           RedirectAttributes redirectAttributes) {
        System.out.println("Errors: " + bindingResult.getAllErrors());

        if (registerUserRequest.getPassword() != null &&
                !registerUserRequest.getPassword().equals(registerUserRequest.getRepeatPassword())) {
            bindingResult.addError(
                    new FieldError("registerUserDTO", "repeatPassword", "Passwords do not match")
            );
        }

        boolean isValid = userService.validateUserRegistration(registerUserRequest);
        if (bindingResult.hasErrors() || !isValid) {
            redirectAttributes.addFlashAttribute("registerUserDTO", registerUserRequest);
            redirectAttributes.addFlashAttribute(
                    "org.springframework.validation.BindingResult.registerUserDTO", bindingResult);
            return "redirect:/register";
        }
        userService.register(registerUserRequest);
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @PostMapping("/login")
    public String login(LoginUserRequest loginUserRequest,
                        RedirectAttributes redirectAttributes) {

        boolean login = userService.login(loginUserRequest.getUsername(), loginUserRequest.getPassword());
        if (!login) {
            redirectAttributes.addFlashAttribute("message", "Invalid username or password");
            redirectAttributes.addFlashAttribute("loginError", true);
            return "redirect:/login";
        }
        currentUser.login(loginUserRequest.getUsername());

        return "redirect:/home";
    }

    @GetMapping("/logout")
    public String logout() {
        currentUser.logout();
        return "redirect:/";
    }

    @GetMapping("/register/TermsAndPrivacy")
    public String TermsAndConditions() {
        return "TermsAndConditions";
    }
}

package bg.softuni.Jukebox.controller;

import bg.softuni.Jukebox.model.dto.LoginUserRequest;
import bg.softuni.Jukebox.model.dto.RegisterUserRequest;
import bg.softuni.Jukebox.service.UserService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
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

        return "register";
    }

    @PostMapping("/register")
    public String register(@Valid RegisterUserRequest registerUserRequest,
                           BindingResult bindingResult,
                           RedirectAttributes redirectAttributes) {

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
    public String login(@RequestParam(value = "error", required = false) String errorParam, Model model) {
        if (errorParam != null) {
            model.addAttribute("errorMessage", "Incorrect username or password");
        }
        return "login";
    }

    @GetMapping("/logout")
    public String logout() {
        return "redirect:/";
    }

    @GetMapping("/register/terms-and-privacy")
    public String TermsAndConditions() {
        return "terms-and-conditions";
    }
}

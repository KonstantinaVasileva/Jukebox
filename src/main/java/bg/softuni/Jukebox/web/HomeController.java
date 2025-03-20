package bg.softuni.Jukebox.web;

import bg.softuni.Jukebox.comment.Comment;
import bg.softuni.Jukebox.user.User;
import bg.softuni.Jukebox.security.AuthenticationMetadata;
import bg.softuni.Jukebox.comment.CommentService;
import bg.softuni.Jukebox.user.UserService;
import bg.softuni.Jukebox.web.dto.LoginUserRequest;
import bg.softuni.Jukebox.web.dto.RegisterUserRequest;
import jakarta.validation.Valid;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.UUID;

@Controller
public class HomeController {

    private final UserService userService;
    private final CommentService commentService;

    public HomeController(UserService userService, CommentService commentService) {
        this.userService = userService;
        this.commentService = commentService;
    }

    @ModelAttribute("registerUserRequest")
    public RegisterUserRequest registerUserRequest() {
        return new RegisterUserRequest();
    }

    @ModelAttribute("loginUserRequest")
    public LoginUserRequest getLoginUserRequest() {
        return new LoginUserRequest();
    }

    @GetMapping("/")
    public String getIndex() {

        return "index";
    }

    @GetMapping("/home")
    public String getHome(@AuthenticationPrincipal AuthenticationMetadata metadata, Model model) {
        UUID userId = metadata.getId();
        User user = userService.findById(userId);
        model.addAttribute("user", user);
        List<Comment> nonDeletedCommentsByUser = commentService.getNonDeletedCommentsByUser(userId);
        model.addAttribute("nonDeletedCommentsByUser", nonDeletedCommentsByUser.size());
        return "home";
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
                    new FieldError("registerUserRequest", "repeatPassword", "Passwords do not match")
            );
        }
        userService.validateUserRegistration(registerUserRequest);
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("registerUserRequest", registerUserRequest);
            redirectAttributes.addFlashAttribute(
                    "org.springframework.validation.BindingResult.registerUserRequest", bindingResult);
            return "redirect:/register";
        }
        userService.register(registerUserRequest);
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String login(@RequestParam(value = "error", required = false) String errorParam,
                        Model model) {

        if (errorParam != null) {
            model.addAttribute("errorMessage", "Incorrect username or password");
        }

        return "login";
    }

    @GetMapping("/register/terms-and-privacy")
    public String TermsAndConditions() {
        return "terms-and-conditions";
    }
}

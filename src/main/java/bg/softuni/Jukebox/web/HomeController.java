package bg.softuni.Jukebox.web;

import bg.softuni.Jukebox.model.entity.Comment;
import bg.softuni.Jukebox.model.entity.User;
import bg.softuni.Jukebox.security.AuthenticationMetadata;
import bg.softuni.Jukebox.service.CommentService;
import bg.softuni.Jukebox.service.UserService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

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
}

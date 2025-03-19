package bg.softuni.Jukebox.web;

import bg.softuni.Jukebox.web.dto.CommentForm;
import bg.softuni.Jukebox.model.entity.Band;
import bg.softuni.Jukebox.model.entity.Comment;
import bg.softuni.Jukebox.model.entity.User;
import bg.softuni.Jukebox.service.BandService;
import bg.softuni.Jukebox.service.CommentService;
import bg.softuni.Jukebox.service.UserService;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/comments")
public class CommentController {

    private final CommentService commentService;
    private final BandService bandService;
    private final UserService userService;

    public CommentController(CommentService commentService, BandService bandService, UserService userService) {
        this.commentService = commentService;
        this.bandService = bandService;
        this.userService = userService;
    }

    @ModelAttribute("loggedInUser")
    public User getLoggedInUser(Principal principal) {
        return userService.findByUsername(principal.getName());
    }

    @GetMapping("/add/{id}")
    public String addComment(@PathVariable UUID id, Model model) {
        Band band = bandService.findById(id);
        model.addAttribute("band", band);
        model.addAttribute("addComment", new CommentForm());
        return "comment-add";
    }

    @PostMapping("/add/{id}")
    public String addComment(@PathVariable UUID id,
                             @Valid CommentForm comment,
                             BindingResult bindingResult,
                             Model model,
                             Principal principal) {

        if (bindingResult.hasErrors()) {
            return "comment-add";
        }

        Band band = bandService.findById(id);
        model.addAttribute("band", band);
        String username = principal.getName();
        User author = userService.findByUsername(username);
        commentService.addComment(author, comment, id);

        return "redirect:/comments/{id}";
    }

    @GetMapping("/{id}")
    public String getComments(@PathVariable UUID id, Model model) {
        Band band = bandService.findById(id);
        model.addAttribute("band", band);
        List<Comment> allComments = commentService.allComments(id);
        model.addAttribute("comments", allComments);
        return "comments-all";
    }

    @PreAuthorize("@commentSecurityService.isOwnerOrAdmin(#id, authentication)")
    @GetMapping("/delete/{id}")
    public String deleteComment(@PathVariable UUID id) {
        UUID bandId = commentService.getBandIdByCommentId(id);
        commentService.setCommentToDelete(id);
        return "redirect:/comments/" + bandId;
    }

    @GetMapping("/report/{id}")
    public String report(@PathVariable UUID id, Model model) {
        UUID bandId = commentService.getBandIdByCommentId(id);
        commentService.reportComment(id);
        List<Comment> reportedComments = commentService.getReportedComments();
        model.addAttribute("reportedComments", reportedComments);
        return "redirect:/comments/" + bandId;
    }
}

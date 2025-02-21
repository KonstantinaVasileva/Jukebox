package bg.softuni.Jukebox.controller;

import bg.softuni.Jukebox.model.dto.CommentForm;
import bg.softuni.Jukebox.model.entity.Band;
import bg.softuni.Jukebox.model.entity.Comment;
import bg.softuni.Jukebox.service.BandService;
import bg.softuni.Jukebox.service.CommentService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/comments")
public class CommentController {

    private final CommentService commentService;
    private final BandService bandService;

    public CommentController(CommentService commentService, BandService bandService) {
        this.commentService = commentService;
        this.bandService = bandService;
    }

    @GetMapping("/add/{id}")
    public String addComment(@PathVariable UUID id, Model model) {
        Band band = bandService.findById(id);
        model.addAttribute("band", band);
        model.addAttribute("addComment", new CommentForm());
        return "comment-add";
    }

    @PostMapping("/add/{id}")
    public String addComment(@PathVariable UUID id, @Valid CommentForm comment, BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            return "comment-add";
        }

        Band band = bandService.findById(id);
        model.addAttribute("band", band);
        commentService.addComment(comment, id);

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

}

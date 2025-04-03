package bg.softuni.Jukebox.web;

import bg.softuni.Jukebox.comment.CommentService;
import bg.softuni.Jukebox.user.User;
import bg.softuni.Jukebox.user.UserService;
import bg.softuni.Jukebox.web.dto.SwitchUserRole;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Controller
@RequestMapping("users")
public class UserController {

    private final UserService userService;
    private final CommentService commentService;

    public UserController(UserService userService, CommentService commentService) {
        this.userService = userService;
        this.commentService = commentService;
    }

    @ModelAttribute("switchRole")
    public SwitchUserRole getSwitchRole() {
        return new SwitchUserRole();
    }

    @GetMapping("/all")
    public String allUsers(Model model) {
        List<User> allUsers = userService.getAllUsers();
        model.addAttribute("users", allUsers);

        Map<UUID, Integer> deletedCommentsCount = commentService.getDeletedComments();
        model.addAttribute("deletedComments", deletedCommentsCount);

        return "users-all";
    }

    @PostMapping("/{id}/ban")
    public String switchUserBan(@PathVariable UUID id) {
        userService.switchBan(id);
        return "redirect:/users/all";
    }

    @PostMapping("/{id}/role")
    public String switchUserRole(@PathVariable UUID id, SwitchUserRole switchUserRole) {
        userService.switchRole(id, switchUserRole);
        return "redirect:/users/all";
    }

    @DeleteMapping("/{id}/delete")
    public String deleteUser(@PathVariable UUID id) {
        userService.deleteUser(id);
        return "redirect:/users/all";
    }
}

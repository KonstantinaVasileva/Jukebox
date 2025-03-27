package bg.softuni.Jukebox.web;

import bg.softuni.Jukebox.notification.client.dto.Notification;
import bg.softuni.Jukebox.notification.service.NotificationService;
import bg.softuni.Jukebox.security.AuthenticationMetadata;
import bg.softuni.Jukebox.user.User;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/notifications")
public class NotificationController {

    private final NotificationService notificationService;

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @GetMapping("/all/{id}")
    public String all(@PathVariable UUID id, Model model) {

        List<Notification> allNotification = notificationService.getAllNotification(id);
        model.addAttribute("notifications", allNotification);

        return "notifications-all";
    }

    @GetMapping("/{id}")
    public String showNotification(@PathVariable UUID id, @AuthenticationPrincipal AuthenticationMetadata authenticationMetadata, Model model) {
        UUID userId = authenticationMetadata.getId();
        User user = notificationService.getUser(userId);
        model.addAttribute("user", user);
        Notification notification = notificationService.getNotification(id, userId);
        model.addAttribute("notification", notification);
        return "notification";
    }

    @GetMapping("/read/{id}")
    public String setToRead(@PathVariable UUID id, Model model) {
        Notification notification = notificationService.readNotification(id);
        model.addAttribute("notification", notification);
        return "redirect:/notifications/" + id;
    }
}

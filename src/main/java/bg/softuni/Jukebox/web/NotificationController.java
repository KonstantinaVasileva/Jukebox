package bg.softuni.Jukebox.web;

import bg.softuni.Jukebox.notification.client.dto.Notification;
import bg.softuni.Jukebox.notification.service.NotificationService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
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
    public String showNotification(@PathVariable UUID id, Model model) {
        Notification notification = notificationService.getNotification(id);
        model.addAttribute("notification", notification);
        return "notification";
    }
}

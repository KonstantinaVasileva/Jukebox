package bg.softuni.Jukebox.notification.client;

import bg.softuni.Jukebox.notification.client.dto.Notification;
import bg.softuni.Jukebox.notification.client.dto.NotificationRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.UUID;

@FeignClient(name = "notification-svc", url = "http://localhost:8081/api/v1/notifications")
public interface NotificationClient {

    @PostMapping("/send")
    ResponseEntity<Notification> sendNotification(NotificationRequest notificationRequest);

    @GetMapping("/all/{id}")
    ResponseEntity<List<Notification>> allNotification(@PathVariable UUID id);

    @GetMapping("/{id}")
    Notification getNotification(@PathVariable UUID id);
}

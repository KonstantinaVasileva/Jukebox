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

    @GetMapping("/read/{id}")
    ResponseEntity<Notification> readNotification(@PathVariable UUID id);

    @GetMapping("/warning-status/{id}")
    ResponseEntity<List<Notification>> warningStatus(@PathVariable UUID id);
}

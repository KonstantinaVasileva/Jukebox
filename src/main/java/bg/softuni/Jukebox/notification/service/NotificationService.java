package bg.softuni.Jukebox.notification.service;

import bg.softuni.Jukebox.notification.client.NotificationClient;
import bg.softuni.Jukebox.notification.client.dto.Notification;
import bg.softuni.Jukebox.notification.client.dto.NotificationRequest;
import bg.softuni.Jukebox.user.User;
import bg.softuni.Jukebox.user.UserService;
import bg.softuni.Jukebox.web.dto.RegisterUserRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Slf4j
@Service
public class NotificationService {

    private final NotificationClient notificationClient;
    private final UserService userService;

    public NotificationService(NotificationClient notificationClient, @Lazy UserService userService) {
        this.notificationClient = notificationClient;
        this.userService = userService;
    }

    public Notification sendWelcomeNotification(RegisterUserRequest userRequest) {

        User byUsername = userService.findByUsername(userRequest.getUsername());
        NotificationRequest notificationRequest = NotificationRequest.builder()
                .title("Welcome to Jukebox")
                .body("Get ready to rock! We're so excited to welcome you to Jukebox. Your music journey starts now!")
                .type("WELCOME")
                .recipientId(byUsername.getId())
                .build();

        ResponseEntity<Notification> httpResponse = null;

        try {
            httpResponse = notificationClient.sendNotification(notificationRequest);
            if (!httpResponse.getStatusCode().is2xxSuccessful()) {
                log.error(Objects.requireNonNull(httpResponse.getBody()).toString());
            }
        } catch (Exception e) {
            log.warn(e.getMessage());
        }

        return Objects.requireNonNull(httpResponse).getBody();
    }

    public List<Notification> getAllNotification(UUID id) {
        ResponseEntity<List<Notification>> listResponseEntity = null;
        try {
            listResponseEntity = notificationClient.allNotification(id);
            if (!listResponseEntity.getStatusCode().is2xxSuccessful()) {
                log.error(Objects.requireNonNull(listResponseEntity.getBody()).toString());
            }
        } catch (Exception e) {
            log.warn(e.getMessage());
        }
        return listResponseEntity.getBody();
    }

    public Notification getNotification(UUID id, UUID userId) {
        return
                getAllNotification(userId).stream()
                        .filter(n -> n.getId().equals(id))
                        .findFirst().orElseThrow(() -> new RuntimeException("Notification not found"));
    }

    public User getUser(UUID userId) {
        return userService.findById(userId);
    }

    public Notification readNotification(UUID id) {
        ResponseEntity<Notification> responseEntity = null;
        try {
            responseEntity = notificationClient.readNotification(id);
            if (!responseEntity.getStatusCode().is2xxSuccessful()) {
                throw new RuntimeException("Failed to read notification");
            }
        } catch (Exception e) {
            throw new RuntimeException("Error reading notification", e);
        }
        return Objects.requireNonNull(responseEntity).getBody();
    }
}

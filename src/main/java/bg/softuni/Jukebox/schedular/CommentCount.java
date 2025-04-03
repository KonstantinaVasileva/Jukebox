package bg.softuni.Jukebox.schedular;

import bg.softuni.Jukebox.notification.client.dto.Notification;
import bg.softuni.Jukebox.notification.service.NotificationService;
import bg.softuni.Jukebox.security.AuthenticationMetadata;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class CommentCount {

    private final NotificationService notificationService;

    @Autowired
    public CommentCount(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

//    @Scheduled(cron = "0 0 0 * * ?")
    @Scheduled(fixedDelay = 50000)
    public void countWarningCommentsEveryDay() {

        notificationService.sendInfoNotificationToAllUsers();
    }
}

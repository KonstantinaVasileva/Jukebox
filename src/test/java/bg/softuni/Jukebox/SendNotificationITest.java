package bg.softuni.Jukebox;

import bg.softuni.Jukebox.notification.client.dto.NotificationRequest;
import bg.softuni.Jukebox.notification.service.NotificationService;
import bg.softuni.Jukebox.user.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@SpringBootTest
public class SendNotificationITest {

    @Autowired
    private NotificationService notificationService;

    @Test
    void sendNotificationTest_happyPath() {

        User user = User.builder()
                .id(UUID.randomUUID())
                .username("username")
                .password("password")
                .build();

        notificationService.sendInfoNotificationToAllUsers();

        assertEquals(1, notificationService.getAllNotification(user.getId()).size());

    }
}

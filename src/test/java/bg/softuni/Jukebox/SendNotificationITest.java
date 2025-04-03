package bg.softuni.Jukebox;

import bg.softuni.Jukebox.notification.client.dto.Notification;
import bg.softuni.Jukebox.notification.client.dto.NotificationRequest;
import bg.softuni.Jukebox.notification.service.NotificationService;
import bg.softuni.Jukebox.user.Role;
import bg.softuni.Jukebox.user.User;
import bg.softuni.Jukebox.user.UserRepository;
import bg.softuni.Jukebox.user.UserService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@SpringBootTest
@Transactional
public class SendNotificationITest {

    @Autowired
    private NotificationService notificationService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;

    @Test
    void sendNotificationTest_happyPath() {

        User user = User.builder()
                .email("test@gmail.com")
                .username("username")
                .password("password")
                .role(Role.USER)
                .banned(false)
                .build();

        userRepository.save(user);

        notificationService.sendInfoNotificationToAllUsers();

        List<Notification> notifications = notificationService.getAllNotification(user.getId());

        assertEquals(1, notifications.size());

    }
}

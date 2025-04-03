package bg.softuni.Jukebox.web;

import bg.softuni.Jukebox.notification.client.dto.Notification;
import bg.softuni.Jukebox.notification.service.NotificationService;
import bg.softuni.Jukebox.security.AuthenticationMetadata;
import bg.softuni.Jukebox.user.Role;
import bg.softuni.Jukebox.user.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = NotificationController.class)
public class NotificationControllerApiTest {

    @MockitoBean
    public NotificationService notificationService;

    @Autowired
    MockMvc mockMvc;

    @Test
    void getShowNotifications_happyPath() throws Exception {

        Notification notification = Notification.builder()
                .id(UUID.randomUUID())
                .title("title")
                .body("body")
                .readed(false).build();

        User user = User.builder()
                .id(UUID.randomUUID())
                .build();

        AuthenticationMetadata principal = new AuthenticationMetadata(UUID.randomUUID(),
                "username",
                "password",
                Role.USER,
                false);

        when(notificationService.getNotification(any(), any())).thenReturn(notification);
        when(notificationService.getUser(any())).thenReturn(user);
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/notifications/{id}", notification.getId())
                .with(user(principal))
                .with(csrf());

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("user"))
                .andExpect(model().attributeExists("notification"))
                .andExpect(view().name("notification"));
    }
}

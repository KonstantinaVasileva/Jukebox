package bg.softuni.Jukebox.notification.client.dto;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class NotificationRequest {

    private String title;

    private String body;

    private String type;

    private UUID recipientId;
}

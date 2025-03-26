package bg.softuni.Jukebox.notification.client.dto;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class Notification {

    private UUID id;

    private String title;

    private String body;

    private boolean readed;
}

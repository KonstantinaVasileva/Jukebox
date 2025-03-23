package bg.softuni.Jukebox.comment;

import bg.softuni.Jukebox.band.Band;
import bg.softuni.Jukebox.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "comments")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(columnDefinition = "TEXT")
    private String content;

    private LocalDateTime createdOn;

    private boolean reported;

    private boolean deleted;

    @ManyToOne
    private User author;

    @ManyToOne
    private Band band;
}

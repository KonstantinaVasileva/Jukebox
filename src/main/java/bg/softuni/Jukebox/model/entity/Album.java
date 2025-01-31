package bg.softuni.Jukebox.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "albums")
@NoArgsConstructor
@Data
@Builder
@AllArgsConstructor
public class Album {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(nullable = false)
    private String title;
    private int releaseDate;
    private String description;
    @ManyToOne
    @JoinColumn(nullable = false)
    private Band band;


}

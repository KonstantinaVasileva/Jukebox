package bg.softuni.Jukebox.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table
@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class Song {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String title;
    private LocalDate releaseDate;
    @Enumerated(EnumType.STRING)
    private GenreType genre;
    @ManyToOne
    private Album album;
    @ManyToOne
    private Band band;

}

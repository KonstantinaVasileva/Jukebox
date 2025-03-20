package bg.softuni.Jukebox.song;

import bg.softuni.Jukebox.album.Album;
import bg.softuni.Jukebox.band.Band;
import bg.softuni.Jukebox.genre.GenreType;
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

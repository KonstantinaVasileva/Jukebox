package bg.softuni.Jukebox.album;

import bg.softuni.Jukebox.band.Band;
import bg.softuni.Jukebox.song.Song;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
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

    @Column(columnDefinition = "TEXT")
    private String description;

    @ManyToOne
    private Band band;

    @OneToMany(mappedBy = "album")
    private List<Song> songs;
}

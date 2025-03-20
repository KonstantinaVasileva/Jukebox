package bg.softuni.Jukebox.band;

import bg.softuni.Jukebox.album.Album;
import bg.softuni.Jukebox.genre.Genre;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "bands")
@Getter
@Setter
@NoArgsConstructor
public class Band {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(nullable = false, unique = true)
    private String name;
    private int formed;
    @Column(columnDefinition = "TEXT")
    private String description;
    private String image;
    @ManyToOne
    private Genre genre;
    @OneToMany(mappedBy = "band")
    private Set<Album> albums;
}

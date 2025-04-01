package bg.softuni.Jukebox.genre;

import bg.softuni.Jukebox.band.Band;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "genres")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Genre {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Enumerated(EnumType.STRING)
    private GenreType name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @OneToMany(mappedBy = "genre")
    private List<Band> bands;
}

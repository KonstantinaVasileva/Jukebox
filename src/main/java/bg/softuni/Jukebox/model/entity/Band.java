package bg.softuni.Jukebox.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
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
    private LocalDate formed;
    private String description;
    @ManyToMany
    private Set<Genre> genres;
    @OneToMany(mappedBy = "band")
    private Set<Album> albums;
}

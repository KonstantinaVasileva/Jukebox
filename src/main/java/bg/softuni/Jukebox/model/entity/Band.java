package bg.softuni.Jukebox.model.entity;

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
    @ManyToOne
    private Genre genre;
    @OneToMany(mappedBy = "band")
    private Set<Album> albums;
}

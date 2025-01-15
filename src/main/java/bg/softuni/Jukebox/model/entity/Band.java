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
    @ElementCollection(targetClass = GenreType.class)
    @Enumerated(EnumType.STRING)
    private Set<GenreType> genres;
}

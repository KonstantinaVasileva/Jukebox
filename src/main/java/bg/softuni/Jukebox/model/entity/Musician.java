package bg.softuni.Jukebox.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "musicians")
@Getter
@Setter
@NoArgsConstructor
public class Musician {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(nullable = false)
    private String firstName;
    private String lastName;
    private LocalDate birthDate;
    private LocalDate deathDate;
    @ManyToOne
    private Band band;
    private String description;
}

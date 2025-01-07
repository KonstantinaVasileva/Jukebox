package bg.softuni.Jukebox.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "musicians")
@Getter
@Setter
@NoArgsConstructor
public class Musician extends BaseEntity {
    @Column(nullable = false)
    private String firstName;
    private String lastName;
    @Column(name = "birth_date")
    private LocalDate birthDate;
    @Column(name = "deathDate")
    private LocalDate deathDate;
    @ManyToOne
    private Band band;
    private String description;
}

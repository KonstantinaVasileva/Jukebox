package bg.softuni.Jukebox.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "albums")
@Getter
@Setter
@NoArgsConstructor
public class Album extends BaseEntity {
    @Column(nullable = false)
    private String title;
    private LocalDate releaseDate;
    private String description;
    @ManyToOne
    @JoinColumn(nullable = false)
    private Band band;
}

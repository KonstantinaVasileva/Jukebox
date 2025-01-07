package bg.softuni.Jukebox.model.entity;

import bg.softuni.Jukebox.model.entity.enums.GenreType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table
@Getter
@Setter
@NoArgsConstructor
public class Song extends BaseEntity {
    private String title;
    private LocalDate releaseDate;
    @Enumerated(EnumType.STRING)
    private GenreType genre;
    @ManyToOne
    private Album album;
    @ManyToOne
    private Band band;

}

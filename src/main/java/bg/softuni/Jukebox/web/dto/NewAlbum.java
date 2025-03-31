package bg.softuni.Jukebox.web.dto;

import bg.softuni.Jukebox.band.Band;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class NewAlbum {

    @NotNull
    private String title;

    @NotNull
    private int releaseDate;

    @NotNull
    private String description;

    private Band band;

    public NewAlbum(Band band) {
        this.band = band;
    }
}

package bg.softuni.Jukebox.model.dto;

import com.google.gson.annotations.Expose;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BandSeed {
    @Expose
    private String name;
    @Expose
    private int formed;
    @Expose
    private String description;
    @Expose
    private String image;
    @Expose
    private String genre;
}

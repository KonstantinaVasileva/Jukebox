package bg.softuni.Jukebox.web.dto;

import com.google.gson.annotations.Expose;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class GenreSeed {
    @Expose
    private String name;

    @Expose
    private String description;
}

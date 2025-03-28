package bg.softuni.Jukebox.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/artist")
public class ArtistController {

    @GetMapping("/add/album")
    public String addAlbum() {
        return "album-add";
    }
}

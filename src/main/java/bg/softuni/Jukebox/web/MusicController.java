package bg.softuni.Jukebox.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MusicController {

    @GetMapping("/genre")
    public String genre(Model model) {
        return "genre";
    }
    @GetMapping("/bands")
    public String band(Model model) {
        return "bands-all";
    }
    @GetMapping("/albums")
    public String album(Model model) {
        return "albums-all";
    }
    @GetMapping("/songs")
    public String song(Model model) {
        return "songs-all";
    }

}

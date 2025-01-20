package bg.softuni.Jukebox.controller;

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
        return "bands";
    }
    @GetMapping("/albums")
    public String album(Model model) {
        return "album";
    }
    @GetMapping("/songs")
    public String song(Model model) {
        return "song";
    }

}

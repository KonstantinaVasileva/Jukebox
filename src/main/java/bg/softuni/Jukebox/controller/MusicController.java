package bg.softuni.Jukebox.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MusicController {

    @GetMapping("/bands")
    public String bands(Model model) {
        return "bands";
    }
}

package bg.softuni.Jukebox.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CommonController {

    @GetMapping("/rating")
    public String rating() {
        return "rating";
    }

    @GetMapping("/offers")
    public String offers() {
        return "price-table";
    }
}

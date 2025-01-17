package bg.softuni.Jukebox.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/search")
public class SearchController {

    @GetMapping
    public String search(@RequestParam String query) {
//        List<String> items =  List.of("GENRE", "BANDS", "ALBUM", "SONG");
//        String string = items.stream()
//                .filter(item -> item.toLowerCase().contains(query.toLowerCase()))
//                .toString();

        return switch (query.toLowerCase()) {
            case "genre" -> "redirect:/search/genre";
            case "bands" -> "redirect:/search/bands";
            case "album" -> "redirect:/search/albums";
            case "song" -> "redirect:/search/songs";
            default -> "redirect:/home";
        };
    }

}

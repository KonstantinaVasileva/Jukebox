package bg.softuni.Jukebox.controller;

import bg.softuni.Jukebox.model.dto.SearchRequest;
import bg.softuni.Jukebox.service.GenreService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/search")
public class SearchController {

    private final GenreService genreService;

    public SearchController(GenreService genreService) {
        this.genreService = genreService;
    }

    @ModelAttribute("searchRequest")
    public SearchRequest searchRequest() {
        return new SearchRequest();
    }


    @GetMapping
    public String search(@RequestParam String query,
                         SearchRequest searchRequest) {
//        List<String> items =  List.of("GENRE", "BANDS", "ALBUM", "SONG");
//        String string = items.stream()
//                .filter(item -> item.toLowerCase().contains(query.toLowerCase()))
//                .toString();

        switch (query.toLowerCase()) {
            case "genre":
                genreService.find(searchRequest.getTitle());
                break;
            case "bands":
                break;
            case "album":
                break;
            case "song":
                break;
        }
    }

}

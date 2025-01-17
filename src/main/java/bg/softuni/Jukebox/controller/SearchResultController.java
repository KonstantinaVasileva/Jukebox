package bg.softuni.Jukebox.controller;

import bg.softuni.Jukebox.model.dto.SearchRequest;
import bg.softuni.Jukebox.model.entity.Band;
import bg.softuni.Jukebox.service.GenreService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/search")
public class SearchResultController {

    private final GenreService genreService;

    public SearchResultController(GenreService genreService) {
        this.genreService = genreService;
    }

    @ModelAttribute("searchRequest")
    public SearchRequest searchRequest() {
        return new SearchRequest();
    }

    @GetMapping("/genre")
    public List<Band> genre(SearchRequest searchRequest) {
        return genreService.findBandByGenre(searchRequest.getTitle());
    }

    //case "genre":
    //                genreService.findBandByGenre(searchRequest.getTitle());
    //                break;
    //            case "bands":
    //                bandService.findAlbumsByBandName(searchRequest.getTitle());
    //                break;
    //            case "album":
    //                albumService.findAlbumByTitle(searchRequest.getTitle());
    //                break;
    //            case "song":
    //                songService.findSongByTitle(searchRequest.getTitle());
    //                break;
}

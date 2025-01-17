package bg.softuni.Jukebox.controller;

import bg.softuni.Jukebox.model.dto.SearchRequest;
import bg.softuni.Jukebox.service.AlbumService;
import bg.softuni.Jukebox.service.BandService;
import bg.softuni.Jukebox.service.GenreService;
import bg.softuni.Jukebox.service.SongService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/search")
public class SearchController {

    private final GenreService genreService;
    private final BandService bandService;
    private final AlbumService albumService;
    private final SongService songService;

    public SearchController(GenreService genreService, BandService bandService, AlbumService albumService, SongService songService) {
        this.genreService = genreService;
        this.bandService = bandService;
        this.albumService = albumService;
        this.songService = songService;
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
                genreService.findBandByGenre(searchRequest.getTitle());
                break;
            case "bands":
                bandService.findAlbumsByBandName(searchRequest.getTitle());
                break;
            case "album":
                albumService.findAlbumByTitle(searchRequest.getTitle());
                break;
            case "song":
                songService.findSongByTitle(searchRequest.getTitle());
                break;
        }
        return null;
    }

}

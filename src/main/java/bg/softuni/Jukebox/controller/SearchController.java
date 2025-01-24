package bg.softuni.Jukebox.controller;

import bg.softuni.Jukebox.model.entity.Album;
import bg.softuni.Jukebox.model.entity.Band;
import bg.softuni.Jukebox.model.entity.Song;
import bg.softuni.Jukebox.service.AlbumService;
import bg.softuni.Jukebox.service.BandService;
import bg.softuni.Jukebox.service.GenreService;
import bg.softuni.Jukebox.service.SongService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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

//    @GetMapping
//    public String search(@RequestParam String query) {
//        List<String> items =  List.of("GENRE", "BANDS", "ALBUM", "SONG");
//        return items.stream()
//                .filter(item -> item.toLowerCase().contains(query.toLowerCase()))
//                .toString();
//

    /// /        return switch (query.toLowerCase()) {
    /// /            case "genre" -> "redirect:/search/genre";
    /// /            case "bands" -> "redirect:/search/bands";
    /// /            case "album" -> "redirect:/search/albums";
    /// /            case "song" -> "redirect:/search/songs";
    /// /            default -> "redirect:/home";
    /// /        };
//    }

// @GetMapping
//    public List<SearchResultDto> search(@RequestParam String query, @RequestParam String category) {
//        return switch (category.toLowerCase()) {
//            case "genre" -> genreService.findBandsByGenre(query);
//            case "band" -> bandService.searchBandsByName(query);
//            case "album" -> albumService.searchAlbumsByTitle(query);
//            case "song" -> songService.searchSongsByTitle(query);
//            default -> List.of(); // Empty list for unknown category
//        };
//    }
    @GetMapping
    public List<String> search(@RequestParam String query, @RequestParam String category) {
        List<String> result;
        switch (category.toLowerCase()) {
            case "genre" -> result = genreService.findBandByGenre(query)
                    .stream()
                    .map(Band::getName)
                    .toList();
            case "bands" -> result = bandService.findBandByName(query)
                    .stream()
                    .map(Band::getName)
                    .toList();
            case "album" -> result = albumService.findAlbumByTitle(query)
                    .stream()
                    .map(Album::getTitle)
                    .toList();
            case "song" -> result = songService.findSongByTitle(query)
                    .stream()
                    .map(Song::getTitle)
                    .toList();
            default ->
                    throw new IllegalArgumentException("Invalid category: " + category); // Return an empty list for invalid categories
        }
        return result;
    }

}

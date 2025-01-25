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
import java.util.Map;

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
    public List<?> search(@RequestParam String query, @RequestParam String category) {
        List<?> result;
        switch (category.toLowerCase()) {
            case "genre" -> result = genreService.findBandByGenre(query);
            case "bands" -> result = bandService.findBandBySearch(query);
            case "album" -> result = albumService.findAlbumBySearch(query);
            case "song" -> result = songService.findSongByTitle(query);
            default -> throw new IllegalArgumentException("Invalid category: " + category);
        }

        return result.stream().map(item -> {
            if (item instanceof Band band) {
                return Map.of("id", band.getId(), "name", band.getName());
            } else if (item instanceof Album album) {
                return Map.of("id", album.getId(), "name", album.getTitle());
            } else if (item instanceof Song song) {
                return Map.of("id", song.getId(), "name", song.getTitle());
            } else {
                throw new IllegalStateException("Unexpected object type: " + item.getClass().getSimpleName());
            }
        }).toList();
    }

}

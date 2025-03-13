package bg.softuni.Jukebox.web;

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

    @GetMapping
    public List<?> search(@RequestParam String query, @RequestParam String category) {
        List<?> result;
        switch (category.toLowerCase()) {
            case "genre" -> result = genreService.findBandByGenre(query);
            case "band" -> result = bandService.findBandBySearch(query);
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

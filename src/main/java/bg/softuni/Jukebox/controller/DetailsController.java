package bg.softuni.Jukebox.controller;

import bg.softuni.Jukebox.model.entity.Album;
import bg.softuni.Jukebox.model.entity.Band;
import bg.softuni.Jukebox.model.entity.Song;
import bg.softuni.Jukebox.service.AlbumService;
import bg.softuni.Jukebox.service.BandService;
import bg.softuni.Jukebox.service.SongService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/details")
public class DetailsController {

    private final BandService bandService;
    private final AlbumService albumService;
    private final SongService songService;

    public DetailsController(BandService bandService, AlbumService albumService, SongService songService) {
        this.bandService = bandService;
        this.albumService = albumService;
        this.songService = songService;
    }

    @GetMapping("/{item}")
    public ResponseEntity<?> getItemDetails(@PathVariable String item) {
        // Fetch details based on the item name
        Band band = bandService.findBandByName(item).getFirst();
        if (band != null) {
            return ResponseEntity.ok(band); // Return the band details
        }

        Album album = albumService.findAlbumByTitle(item).getFirst();
        if (album != null) {
            return ResponseEntity.ok(album); // Return the album details
        }

        Song song = songService.findSongByTitle(item).getFirst();
        if (song != null) {
            return ResponseEntity.ok(song); // Return the song details
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Item not found");
    }
}


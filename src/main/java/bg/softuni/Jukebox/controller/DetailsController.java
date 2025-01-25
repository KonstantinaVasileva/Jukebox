package bg.softuni.Jukebox.controller;

import bg.softuni.Jukebox.model.entity.Album;
import bg.softuni.Jukebox.model.entity.Band;
import bg.softuni.Jukebox.model.entity.Song;
import bg.softuni.Jukebox.service.AlbumService;
import bg.softuni.Jukebox.service.BandService;
import bg.softuni.Jukebox.service.SongService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

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

    @GetMapping("/{id}")
    public ResponseEntity<?> getDetails(@PathVariable UUID id) {

        Object result = null;


        if (bandService.existsById(id)) {
            result = bandService.findById(id);
        } else if (albumService.existsById(id)) {
            result = albumService.findById(id);
        } else if (songService.existsById(id)) {
            result = songService.findById(id);
        }

        if (result == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No details found for the given ID.");
        }

        return ResponseEntity.ok(result);
    }
}


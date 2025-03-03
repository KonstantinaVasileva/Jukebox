package bg.softuni.Jukebox.controller;

import bg.softuni.Jukebox.model.entity.Album;
import bg.softuni.Jukebox.model.entity.Band;
import bg.softuni.Jukebox.model.entity.Song;
import bg.softuni.Jukebox.service.AlbumService;
import bg.softuni.Jukebox.service.BandService;
import bg.softuni.Jukebox.service.SongService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@Controller
@RequestMapping("/details")
public class SearchDetailsController {

    private final BandService bandService;
    private final AlbumService albumService;
    private final SongService songService;

    public SearchDetailsController(BandService bandService, AlbumService albumService, SongService songService) {
        this.bandService = bandService;
        this.albumService = albumService;
        this.songService = songService;
    }

    @GetMapping("/{id}")
    public String getDetails(@PathVariable UUID id, Model model) {

        if (bandService.existsById(id)) {
            Band band = bandService.findById(id);
            model.addAttribute("band", band);
            return "band-details";
        } else if (albumService.existsById(id)) {
            Album album = albumService.findById(id);
            model.addAttribute("album", album);
            return  "album-details";
        } else if (songService.existsById(id)) {
            Song song = songService.findById(id);
            model.addAttribute("song", song);
            return  "song-details";
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Item not found");
        }

    }
}


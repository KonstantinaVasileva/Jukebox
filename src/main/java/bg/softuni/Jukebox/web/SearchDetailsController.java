package bg.softuni.Jukebox.web;

import bg.softuni.Jukebox.album.Album;
import bg.softuni.Jukebox.band.Band;
import bg.softuni.Jukebox.security.AuthenticationMetadata;
import bg.softuni.Jukebox.song.Song;
import bg.softuni.Jukebox.album.AlbumService;
import bg.softuni.Jukebox.band.BandService;
import bg.softuni.Jukebox.song.SongService;
import bg.softuni.Jukebox.user.Role;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
    public String getDetails(@PathVariable UUID id, Model model,@AuthenticationPrincipal AuthenticationMetadata authenticationMetadata) {

        Band band;

        if (bandService.existsById(id)) {
            band = bandService.findById(id);
        } else if (albumService.existsById(id)) {
            Album album = albumService.findById(id);
            band = album.getBand();
        } else if (songService.existsById(id)) {
            Song song = songService.findById(id);
            band = song.getBand();
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Item not found");
        }

        String role = authenticationMetadata.getRole().name();
        model.addAttribute("role", role);

        model.addAttribute("band", band);
        return "band-details";
    }
}


package bg.softuni.Jukebox.web;

import bg.softuni.Jukebox.album.AlbumService;
import bg.softuni.Jukebox.band.Band;
import bg.softuni.Jukebox.band.BandService;
import bg.softuni.Jukebox.web.dto.NewAlbum;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Controller
@RequestMapping("/artist")
public class ArtistController {

    private final AlbumService albumService;
    private final BandService bandService;

    public ArtistController(AlbumService albumService, BandService bandService) {
        this.albumService = albumService;
        this.bandService = bandService;
    }

    @GetMapping("/{id}/add/album")
    public String addAlbum(@PathVariable UUID id, Model model) {
        Band band = bandService.findById(id);
        NewAlbum newAlbum = new NewAlbum(band);

        model.addAttribute("newAlbum", newAlbum);

        return "album-add";
    }

    @PostMapping("/{id}/add/album")
    public String addAlbum(@PathVariable UUID id,
                           @Valid NewAlbum newAlbum,
                           BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "album-add";
        }
        albumService.addNewAlbum(newAlbum);

        return "redirect:/details/" + id;
    }
}

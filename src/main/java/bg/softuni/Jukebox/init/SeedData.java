package bg.softuni.Jukebox.init;

import bg.softuni.Jukebox.album.AlbumService;
import bg.softuni.Jukebox.band.BandService;
import bg.softuni.Jukebox.genre.GenreService;
import bg.softuni.Jukebox.song.SongService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class SeedData implements CommandLineRunner {

    private final GenreService genreService;
    private final BandService bandService;
    private final AlbumService albumService;
    private final SongService songService;

    public SeedData(GenreService genreService, BandService bandService, AlbumService albumService, SongService songService) {
        this.genreService = genreService;
        this.bandService = bandService;
        this.albumService = albumService;
        this.songService = songService;
    }

    @Override
    public void run(String... args) throws Exception {

        genreService.seed();
        bandService.seed();
        albumService.seed();
        songService.seed();
    }
}

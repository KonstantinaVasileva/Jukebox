package bg.softuni.Jukebox.init;

import bg.softuni.Jukebox.service.BandService;
import bg.softuni.Jukebox.service.GenreService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class SeedData implements CommandLineRunner {
    private final GenreService genreService;
    private final BandService bandService;

    public SeedData(GenreService genreService, BandService bandService) {
        this.genreService = genreService;
        this.bandService = bandService;
    }

    @Override
    public void run(String... args) throws Exception {

        genreService.seed();
        bandService.seed();
    }
}

package bg.softuni.Jukebox.band;

import bg.softuni.Jukebox.exception.SearchNotFoundException;
import bg.softuni.Jukebox.exception.SongNotFoundException;
import bg.softuni.Jukebox.genre.GenreService;
import bg.softuni.Jukebox.genre.GenreType;
import bg.softuni.Jukebox.web.dto.BandSeed;
import bg.softuni.Jukebox.genre.Genre;
import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;
import java.util.UUID;

@Service
public class BandService {

    private final BandRepository bandRepository;
    private final Gson gson;
    private final ModelMapper modelMapper;
    private final GenreService genreService;

    public BandService(BandRepository bandRepository, Gson gson, ModelMapper modelMapper, GenreService genreService) {
        this.bandRepository = bandRepository;
        this.gson = gson;
        this.modelMapper = modelMapper;
        this.genreService = genreService;
    }

    public void seed() throws FileNotFoundException {

        if (bandRepository.count() == 0) {
            String fileName = "src/main/resources/files/band.json";
            BandSeed[] bandSeeds = gson.fromJson(new FileReader(fileName), BandSeed[].class);
            for (BandSeed bandSeed : bandSeeds) {
                Band band = modelMapper.map(bandSeed, Band.class);
                Genre genre = genreService.findGenreByName(bandSeed.getGenre());
                band.setGenre(genre);
                bandRepository.save(band);
            }
        }
    }

    public List<Band> findBandBySearch(String name) {

        List<Band> band = bandRepository.findByNameContainingIgnoreCase(name);
        if (band.isEmpty()) {
            throw new SearchNotFoundException("Not found " + name + " band.");
        }
        return band;
    }

    public boolean existsById(UUID id) {
        return bandRepository.existsById(id);
    }

    public Band findById(UUID id) {
        return bandRepository.findById(id)
                .orElseThrow(() -> new SearchNotFoundException("Band not found"));
    }

    public Band findByName(String name) {
        Band band = bandRepository.findByName(name);
        if (band == null) {
            throw new SearchNotFoundException("Not found " + name + " band.");
        }
        return band;
    }

    public List<Band> findByGenre(Genre title) {
        List<Band> bands = bandRepository.findByGenre(title);
        if (bands.isEmpty()) {
            String genre = title.getName().name();
            throw new SearchNotFoundException("Not found band in " + genre + ".");
        }
        return bands;
    }
}

package bg.softuni.Jukebox.band;

import bg.softuni.Jukebox.genre.GenreService;
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
            //TODO
        }
        return band;
    }

    public boolean existsById(UUID id) {
        return bandRepository.existsById(id);
    }

    public Band findById(UUID id) {
        return bandRepository.findById(id).orElse(null);
    }

    public Band findByName(String name) {
        return bandRepository.findByName(name);
    }
}

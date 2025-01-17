package bg.softuni.Jukebox.service;

import bg.softuni.Jukebox.model.dto.BandSeed;
import bg.softuni.Jukebox.model.entity.Album;
import bg.softuni.Jukebox.model.entity.Band;
import bg.softuni.Jukebox.repository.BandRepository;
import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;
import java.util.Optional;

@Service
public class BandService {

    private final BandRepository bandRepository;
    private final Gson gson;
    private final ModelMapper modelMapper;

    public BandService(BandRepository bandRepository, Gson gson, ModelMapper modelMapper) {
        this.bandRepository = bandRepository;
        this.gson = gson;
        this.modelMapper = modelMapper;
    }

    public void seed() throws FileNotFoundException {
        if (bandRepository.count() == 0) {
            String fileName = "src/main/resources/bands.json";
            BandSeed[] bandSeeds = gson.fromJson(new FileReader(fileName), BandSeed[].class);
            for (BandSeed bandSeed : bandSeeds) {
                Band band = modelMapper.map(bandSeed, Band.class);
                bandRepository.save(band);
            }
        }
    }

    public List<Album> findAlbumsByBandName(String name) {
        Optional<Band> band = bandRepository.findByName(name);
        if (band.isEmpty()) {
            //TODO
        }
        return band.get().getAlbums().stream().toList();
    }
}

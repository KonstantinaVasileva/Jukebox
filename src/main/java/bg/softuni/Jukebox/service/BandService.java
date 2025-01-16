package bg.softuni.Jukebox.service;

import bg.softuni.Jukebox.model.entity.Album;
import bg.softuni.Jukebox.model.entity.Band;
import bg.softuni.Jukebox.repository.BandRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BandService {

    private final BandRepository bandRepository;

    public BandService(BandRepository bandRepository) {
        this.bandRepository = bandRepository;
    }

    public List<Album> findBandByName(String name) {
        Band band = bandRepository.findByName(name).get();
        if (band==null) {
            //TODO
        }
        return band.getAlbums().stream().toList();
    }
}

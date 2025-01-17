package bg.softuni.Jukebox.service;

import bg.softuni.Jukebox.model.entity.Album;
import bg.softuni.Jukebox.model.entity.Band;
import bg.softuni.Jukebox.repository.BandRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BandService {

    private final BandRepository bandRepository;

    public BandService(BandRepository bandRepository) {
        this.bandRepository = bandRepository;
    }

    public List<Album> findAlbumsByBandName(String name) {
        Optional<Band> band = bandRepository.findByName(name);
        if (band.isEmpty()) {
            //TODO
        }
        return band.get().getAlbums().stream().toList();
    }
}

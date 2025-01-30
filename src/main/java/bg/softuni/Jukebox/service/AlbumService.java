package bg.softuni.Jukebox.service;

import bg.softuni.Jukebox.model.entity.Album;
import bg.softuni.Jukebox.repository.AlbumRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;

@Service
public class AlbumService {
    private final AlbumRepository albumRepository;

    public AlbumService(AlbumRepository albumRepository) {
        this.albumRepository = albumRepository;
    }

    public List<Album> findAlbumBySearch(String title) {
        List<Album> albums = albumRepository.findByTitleContainingIgnoreCase(title);
        if (albums.isEmpty()) {
            //TODO EXCEPTION
        }
        return albums;
    }


    public boolean existsById(UUID id) {
        return albumRepository.existsById(id);
    }

    public Album findById(UUID id) {
        return albumRepository.findById(id).orElse(null);
    }
}

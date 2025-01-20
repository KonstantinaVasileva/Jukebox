package bg.softuni.Jukebox.service;

import bg.softuni.Jukebox.model.entity.Album;
import bg.softuni.Jukebox.repository.AlbumRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class AlbumService {
    private final AlbumRepository albumRepository;

    public AlbumService(AlbumRepository albumRepository) {
        this.albumRepository = albumRepository;
    }

    public List<Album> findAlbumByTitle(String title) {
        List<Album> albums = albumRepository.findByTitleContainingIgnoreCase(title);
        if (albums.isEmpty()) {
            //TODO EXCEPTION
        }
        return albums;
    }

}

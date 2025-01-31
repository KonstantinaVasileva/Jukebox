package bg.softuni.Jukebox.service;

import bg.softuni.Jukebox.model.entity.Album;
import bg.softuni.Jukebox.repository.AlbumRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class AlbumService {
    private final AlbumRepository albumRepository;
    private final BandService bandService;

    public AlbumService(AlbumRepository albumRepository, BandService bandService) {
        this.albumRepository = albumRepository;
        this.bandService = bandService;
    }

    public List<Album> findAlbumBySearch(String title) {
        List<Album> albums = albumRepository.findByTitleContainingIgnoreCase(title);
        if (albums.isEmpty()) {
            //TODO EXCEPTION
        }
        return albums;
    }

    public Album findAlbumByTitleAndBand(String title, String band) {
        return albumRepository.findByTitleAndBand_Name(title, band).orElse(null);
    }

    public boolean existsById(UUID id) {
        return albumRepository.existsById(id);
    }

    public Album findById(UUID id) {
        return albumRepository.findById(id).orElse(null);
    }

    public void seed() {
        if (albumRepository.count() == 0) {

            List<Album> albums = new ArrayList<>();
            albums.add(Album.builder()
                    .title("Mountain Music")
                    .releaseDate(1982)
                    .description("One of Alabama's most iconic albums, Mountain Music blends traditional country with Southern rock influences. The album was a huge success, reaching #1 on the Billboard Country Albums chart and even winning a Grammy Award.")
                    .band(bandService.findByName("Alabama")).build());

            albums.add(Album.builder()
                    .title("Roll On")
                    .releaseDate(1984)
                    .description("Following their previous success, Roll On continued Alabama’s streak of chart-topping hits. The album focused on family, life on the road, and country pride, resonating with fans across the country.")
                    .band(bandService.findByName("Alabama")).build());

            albumRepository.saveAll(albums);


        }
    }
}

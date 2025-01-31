package bg.softuni.Jukebox.service;

import bg.softuni.Jukebox.model.entity.Song;
import bg.softuni.Jukebox.repository.SongRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class SongService {

    private final SongRepository songRepository;
    private final AlbumService albumService;

    public SongService(SongRepository songRepository, AlbumService albumService) {
        this.songRepository = songRepository;
        this.albumService = albumService;
    }

    public List<Song> findSongByTitle(String title) {
        return songRepository.findByTitleContainingIgnoreCase(title);
    }

    public boolean existsById(UUID id) {
        return songRepository.existsById(id);
    }

    public Song findById(UUID id) {
        return songRepository.findById(id).orElse(null);
    }

    public void seed() {
        if (songRepository.count() == 0) {
            List<Song> songs = new ArrayList<>();
            songs.add(Song.builder()
                    .title("Mountain Music")
                    .album(albumService.findAlbumByTitleAndBand("Mountain Music","Alabama"))
                    .build());
            songs.add(Song.builder()
                    .title("Close Enough to Perfect ")
                    .album(albumService.findAlbumByTitleAndBand("Mountain Music","Alabama"))
                    .build());
            songRepository.saveAll(songs);
        }
    }
}

package bg.softuni.Jukebox.song;

import bg.softuni.Jukebox.album.AlbumService;
import bg.softuni.Jukebox.band.BandService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class SongService {

    private final SongRepository songRepository;
    private final AlbumService albumService;
    private final BandService bandService;

    public SongService(SongRepository songRepository, AlbumService albumService, BandService bandService) {
        this.songRepository = songRepository;
        this.albumService = albumService;
        this.bandService = bandService;
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
                    .album(albumService.findAlbumByTitleAndBand("Mountain Music", "Alabama"))
                    .band(bandService.findByName("Alabama"))
                    .genre(bandService.findByName("Alabama").getGenre().getName())
                    .build());
            songs.add(Song.builder()
                    .title("Close Enough to Perfect ")
                    .album(albumService.findAlbumByTitleAndBand("Mountain Music", "Alabama"))
                    .band(bandService.findByName("Alabama"))
                    .genre(bandService.findByName("Alabama").getGenre().getName())
                    .build());
            songRepository.saveAll(songs);
        }
    }
}

package bg.softuni.Jukebox.service;

import bg.softuni.Jukebox.model.entity.Song;
import bg.softuni.Jukebox.repository.SongRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SongService {

    private final SongRepository songRepository;

    public SongService(SongRepository songRepository) {
        this.songRepository = songRepository;
    }

    public List<Song> findSongByTitle(String title) {
        return songRepository.findByTitleContainingIgnoreCase(title);
    }
}

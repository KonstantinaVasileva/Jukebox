package bg.softuni.Jukebox.song;

import bg.softuni.Jukebox.album.AlbumService;
import bg.softuni.Jukebox.band.BandService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SongServiceUTest {

    @Mock
    private SongRepository songRepository;

    @Mock
    private AlbumService albumService;

    @Mock
    private BandService bandService;

    @InjectMocks
    private SongService songService;

    @Test
    void happyCaseFindSongByTitle() {
        List<Song> songs = songRepository.findByTitleContainingIgnoreCase("song");
        assertNotNull(songs);
        assertEquals(songs.size(), songService.findSongByTitle("song").size());
    }

    @Test
    void happyCaseExistsById() {
        Song song = Song.builder()
                .id(UUID.randomUUID())
                .build();

        when(songRepository.existsById(song.getId())).thenReturn(true);

        songService.existsById(song.getId());

        assertTrue(songService.existsById(song.getId()));
    }

    @Test
    void happyCaseFindById(){
        Song song = Song.builder()
                .id(UUID.randomUUID())
                .build();

        when(songRepository.findById(song.getId())).thenReturn(Optional.of(song));
        assertNotNull(songService.findById(song.getId()));
        assertEquals(song, songService.findById(song.getId()));
    }

}

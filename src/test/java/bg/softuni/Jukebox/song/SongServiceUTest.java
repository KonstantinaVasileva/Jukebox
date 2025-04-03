package bg.softuni.Jukebox.song;

import bg.softuni.Jukebox.album.AlbumService;
import bg.softuni.Jukebox.band.Band;
import bg.softuni.Jukebox.band.BandService;
import bg.softuni.Jukebox.genre.Genre;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

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

    @Test
    void successfullySeedSongs(){

        Band band = new Band();
        band.setName("Band");
        band.setGenre(new Genre());

        when(songRepository.count()).thenReturn(0L);
        when(bandService.findByName(any())).thenReturn(band);
        songService.seed();
        verify(songRepository, times(1)).saveAll(anyList());
    }

}

package bg.softuni.Jukebox.album;

import bg.softuni.Jukebox.band.Band;
import bg.softuni.Jukebox.band.BandService;
import bg.softuni.Jukebox.exception.SearchNotFoundException;
import bg.softuni.Jukebox.web.dto.NewAlbum;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AlbumServiceUTest {

    @Mock
    private AlbumRepository albumRepository;

    @Mock
    private BandService bandService;

    @InjectMocks
    private AlbumService albumService;

    @Test
    void successfullyFindAlbumByTitle() {
        Album album = new Album();
        album.setTitle("Title");
        List<Album> albums = List.of(album);

        when(albumRepository.findByTitleContainingIgnoreCase("Title")).thenReturn(albums);

        assertEquals(albums, albumService.findAlbumBySearch("Title"));
    }

    @Test
    void throwExceptionWhenTryToFindAlbumByTitle() {
        Album album = new Album();
        album.setTitle("Title");

        when(albumRepository.findByTitleContainingIgnoreCase("Title")).thenReturn(new ArrayList<>());

        SearchNotFoundException exception = assertThrows(SearchNotFoundException.class, () -> albumService.findAlbumBySearch("Title"));
        assertEquals("Not found Title album.", exception.getMessage());
    }

    @Test
    void successfullyFindAlbumByTitleAndBand() {
        Album album = new Album();
        Band band = new Band();
        band.setName("Band");
        album.setTitle("Title");
        album.setBand(new Band());

        when(albumRepository.findByTitleAndBand_Name("Title", "Band")).thenReturn(Optional.of(album));

        assertEquals(album, albumService.findAlbumByTitleAndBand(album.getTitle(), band.getName()));
    }

    @Test
    void throwExceptionWhenTryToFindAlbumByTitleAndBand() {
        Album album = new Album();
        Band band = new Band();
        band.setName("Band");
        album.setTitle("Title");
        album.setBand(band);

        when(albumRepository.findByTitleAndBand_Name("Title", "Band")).thenReturn(Optional.empty());

        SearchNotFoundException exception = assertThrows(SearchNotFoundException.class, () -> albumService.findAlbumByTitleAndBand("Title", "Band"));
        assertEquals("Not found Title album.", exception.getMessage());
    }

    @Test
    void returnTrue_whenCheckIfAlbumExists() {
        Album album = new Album();
        album.setId(UUID.randomUUID());

        when(albumRepository.existsById(album.getId())).thenReturn(true);

        assertTrue(albumService.existsById(album.getId()));
    }

    @Test
    void returnFalse_whenCheckIfBandExists() {
        Album album = new Album();
        album.setId(UUID.randomUUID());

        when(albumRepository.existsById(album.getId())).thenReturn(false);

        assertFalse(albumService.existsById(album.getId()));
    }

    @Test
    void happyCase_WhenFindAlbumById() {
        Album album = new Album();
        album.setId(UUID.randomUUID());

        when(albumRepository.findById(album.getId())).thenReturn(Optional.of(album));

        assertEquals(album, albumService.findById(album.getId()));
    }

    @Test
    void throwException_WhenTryToFindAlbumByIdThatDoesNotExist() {
        Album album = new Album();
        album.setId(UUID.randomUUID());

        when(albumRepository.findById(album.getId())).thenReturn(Optional.empty());

        SearchNotFoundException exception = assertThrows(SearchNotFoundException.class, () -> albumService.findById(album.getId()));
        assertEquals("Album not found", exception.getMessage());
    }

    @Test
    void successfullySeedAlbums() {

        when(albumRepository.count()).thenReturn(0L);

        albumService.seed();

        verify(albumRepository, times(1)).saveAll(anyList());
    }

    @Test
    void correctlyAddAlbum() {
        Band band = new Band();
        NewAlbum album = new NewAlbum(band);

        albumService.addNewAlbum(album);

        verify(albumRepository, times(1)).save(any(Album.class));
    }

}

package bg.softuni.Jukebox.band;

import bg.softuni.Jukebox.exception.SearchNotFoundException;
import bg.softuni.Jukebox.genre.Genre;
import bg.softuni.Jukebox.genre.GenreService;
import bg.softuni.Jukebox.genre.GenreType;
import com.google.gson.Gson;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BandServiceUTest {

    @Mock
    private BandRepository bandRepository;

    @Mock
    private Gson gson;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private GenreService genreService;

    @InjectMocks
    private BandService bandService;

    @Test
    void successfullyFindBandByName() {
        Band band = new Band();
        band.setName("Band");
        List<Band> bands = List.of(band);

        when(bandRepository.findByNameContainingIgnoreCase(band.getName())).thenReturn(bands);

        List<Band> result = bandService.findBandBySearch("Band");

        assertEquals(bands, result);
    }

    @Test
    void throwExceptionIfBandNotFound_whenTryFindBandByName() {
        Band band = new Band();
        band.setName("Band");
        List<Band> bands = new ArrayList<>();
        when(bandRepository.findByNameContainingIgnoreCase(band.getName())).thenReturn(bands);

        SearchNotFoundException exception = assertThrows(SearchNotFoundException.class, () -> bandService.findBandBySearch("Band"));

        assertEquals("Not found Band band.", exception.getMessage());
    }

    @Test
    void returnTrue_whenCheckIfBandExists() {
        Band band = new Band();
        band.setId(UUID.randomUUID());

        when(bandRepository.existsById(band.getId())).thenReturn(true);

        assertTrue(bandService.existsById(band.getId()));
    }

    @Test
    void returnFalse_whenCheckIfBandExists() {
        Band band = new Band();
        band.setId(UUID.randomUUID());

        when(bandRepository.existsById(band.getId())).thenReturn(false);

        assertFalse(bandService.existsById(band.getId()));
    }

    @Test
    void happyCase_WhenFindBandById(){
        Band band = new Band();
        band.setId(UUID.randomUUID());

        when(bandRepository.findById(band.getId())).thenReturn(Optional.of(band));

        assertEquals(band, bandService.findById(band.getId()));
    }

    @Test
    void throwException_WhenTryToFindBandByIdThatDoesNotExist() {
        Band band = new Band();
        band.setId(UUID.randomUUID());

        when(bandRepository.findById(band.getId())).thenReturn(Optional.empty());

        SearchNotFoundException exception = assertThrows(SearchNotFoundException.class, () -> bandService.findById(band.getId()));
        assertEquals("Band not found", exception.getMessage());
    }

    @Test
    void happyCase_WhenFindBandByName(){
        Band band = new Band();
        band.setName("Band");

        when(bandRepository.findByName(band.getName())).thenReturn(band);

        assertEquals(band, bandService.findByName(band.getName()));
    }

    @Test
    void throwException_WhenTryToFindBandByNameThatDoesNotExist() {
        Band band = new Band();
        band.setName("Band");

        when(bandRepository.findByName(band.getName())).thenReturn(null);

        SearchNotFoundException exception = assertThrows(SearchNotFoundException.class, () -> bandService.findByName(band.getName()));
        assertEquals("Not found Band band.", exception.getMessage());
    }

    @Test
    void happyCase_WhenFindBandByGenre(){
        Genre genre = new Genre();
        genre.setName(GenreType.BLUES);
        Band band = new Band();
        band.setGenre(genre);

        List<Band> bands = List.of(band);

        when(bandRepository.findByGenre(band.getGenre())).thenReturn(List.of(band));

        assertEquals(bands, bandService.findByGenre(band.getGenre()));
    }

    @Test
    void throwException_WhenTryToFindBandByGenreThatDoesNotExist() {
        Genre genre = new Genre();
        genre.setName(GenreType.BLUES);
        Band band = new Band();
        band.setGenre(genre);

        when(bandRepository.findByGenre(band.getGenre())).thenReturn(new ArrayList<>());

        SearchNotFoundException exception = assertThrows(SearchNotFoundException.class, () -> bandService.findByGenre(band.getGenre()));
        assertEquals("Not found band in " + genre.getName().name() + ".", exception.getMessage());
    }
}

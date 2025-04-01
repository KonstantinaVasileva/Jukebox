package bg.softuni.Jukebox.genre;

import bg.softuni.Jukebox.band.Band;
import bg.softuni.Jukebox.band.BandService;
import bg.softuni.Jukebox.exception.SearchNotFoundException;
import com.google.gson.Gson;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class GenreServiceUTest {

    @Mock
    private GenreRepository genreRepository;

    @Mock
    private Gson gson;

    @Mock
    private BandService bandService;

    @InjectMocks
    private GenreService genreService;

    @Test
    void happyCaseFindBandByGenre() {
        Genre genre = Genre.builder()
                .name(GenreType.ROCK)
                .build();

        when(genreRepository.findByName(GenreType.ROCK)).thenReturn(Optional.of(genre));

        List<Band> expectedBand = List.of(new Band(), new Band());
        when(bandService.findByGenre(genre)).thenReturn(expectedBand);

        List<Band> resultLowerCase = genreService.findBandByGenre("rock");
        List<Band> result = genreService.findBandByGenre("ROCK");

        assertNotNull(result);

        assertEquals(expectedBand.size(), result.size());
        assertEquals(expectedBand.size(), resultLowerCase.size());
    }

    @Test
    void throwExceptionIfGenreNotFound() {
        Genre genre = Genre.builder()
                .name(GenreType.ROCK)
                .build();

        when(genreRepository.findByName(GenreType.ROCK)).thenReturn(Optional.empty());

        SearchNotFoundException exception = assertThrows(SearchNotFoundException.class, () -> genreService.findBandByGenre("ROCK"));
        assertNotNull(exception);
        assertEquals("Not found ROCK genre.", exception.getMessage());
    }
}

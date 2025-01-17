package bg.softuni.Jukebox.service;

import bg.softuni.Jukebox.model.entity.Band;
import bg.softuni.Jukebox.model.entity.Genre;
import bg.softuni.Jukebox.model.entity.GenreType;
import bg.softuni.Jukebox.repository.GenreRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GenreService {
    private final GenreRepository genreRepository;

    public GenreService(GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }

    public List<Band> findBandByGenre(String title) {
        Optional<Genre> genre = genreRepository.findByName(GenreType.valueOf(title));
        if (genre.isEmpty()) {
            //TODO Exceptions
        }

        return genre.get().getBands();
    }
}

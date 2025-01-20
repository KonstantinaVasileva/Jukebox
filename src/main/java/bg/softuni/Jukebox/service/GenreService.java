package bg.softuni.Jukebox.service;

import bg.softuni.Jukebox.model.dto.GenreSeed;
import bg.softuni.Jukebox.model.entity.Band;
import bg.softuni.Jukebox.model.entity.Genre;
import bg.softuni.Jukebox.model.entity.GenreType;
import bg.softuni.Jukebox.repository.GenreRepository;
import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;
import java.util.Optional;

@Service
public class GenreService {

    private final GenreRepository genreRepository;
    private final Gson gson;
    private final ModelMapper modelMapper;

    public GenreService(GenreRepository genreRepository, Gson gson, ModelMapper modelMapper) {
        this.genreRepository = genreRepository;
        this.gson = gson;
        this.modelMapper = modelMapper;
    }

    public void seed() throws FileNotFoundException {
        if (genreRepository.count() == 0) {
            String FILE_PATH = "src/main/resources/files/genre.json";
            GenreSeed[] genreSeeds = gson.fromJson(new FileReader(FILE_PATH), GenreSeed[].class);
            for (GenreSeed genreSeed : genreSeeds) {
                Genre genre = new Genre();
                modelMapper.map(genreSeed, genre);
                genreRepository.save(genre);
            }
        }
    }

    public List<Band> findBandByGenre(String title) {
        return findGenreByName(title.toUpperCase()).getBands();
    }

    public Genre findGenreByName(String name) {
        Optional<Genre> genre = genreRepository.findByName(GenreType.valueOf(name));
        if (genre.isEmpty()) {
            //TODO Exceptions
        }
        return genre.get();
    }
}

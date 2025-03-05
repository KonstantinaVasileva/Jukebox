package bg.softuni.Jukebox.repository;

import bg.softuni.Jukebox.model.entity.Genre;
import bg.softuni.Jukebox.model.entity.GenreType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface GenreRepository extends JpaRepository<Genre, UUID> {

    Optional<Genre> findByName(GenreType name);
}

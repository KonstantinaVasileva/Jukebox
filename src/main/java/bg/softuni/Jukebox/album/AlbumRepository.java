package bg.softuni.Jukebox.album;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface AlbumRepository extends JpaRepository<Album, UUID> {

    List<Album> findByTitleContainingIgnoreCase(String title);

    Optional<Album> findByTitleAndBand_Name(String title, String bandName);
}

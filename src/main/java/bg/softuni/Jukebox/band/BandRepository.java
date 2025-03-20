package bg.softuni.Jukebox.band;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface BandRepository extends JpaRepository<Band, UUID> {

    Band findByName(String name);

    List<Band> findByNameContainingIgnoreCase(String name);


}


package bg.softuni.Jukebox.repository;

import bg.softuni.Jukebox.model.entity.Band;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface BandRepository extends JpaRepository<Band, UUID> {

    List<Band> findByName(String name);
}


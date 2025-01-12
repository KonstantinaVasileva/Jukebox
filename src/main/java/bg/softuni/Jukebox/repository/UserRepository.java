package bg.softuni.Jukebox.repository;

import bg.softuni.Jukebox.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);

    User findByUsernameOrEmail(String username, String email);
}

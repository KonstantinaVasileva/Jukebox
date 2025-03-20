package bg.softuni.Jukebox.comment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CommentRepository extends JpaRepository<Comment, UUID> {
    List<Comment> findByBand_IdOrderByCreatedOn(UUID bandId);

    Optional<Comment> getCommentsById(UUID id);

    List<Comment> findByAuthor_IdAndDeletedIs(UUID authorId, boolean deleted);

    List<Comment> getAllByReported(boolean reported);

    List<Comment> getAllByAuthor_IdAndDeleted(UUID authorId, boolean deleted);
}

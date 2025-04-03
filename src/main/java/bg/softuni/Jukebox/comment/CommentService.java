package bg.softuni.Jukebox.comment;

import bg.softuni.Jukebox.band.BandService;
import bg.softuni.Jukebox.user.UserService;
import bg.softuni.Jukebox.web.dto.CommentForm;
import bg.softuni.Jukebox.band.Band;
import bg.softuni.Jukebox.user.User;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final BandService bandService;
    private final UserService userService;

    public CommentService(CommentRepository commentRepository, BandService bandService, UserService userService) {
        this.commentRepository = commentRepository;
        this.bandService = bandService;
        this.userService = userService;
    }

    public void addComment(User author, CommentForm comment, UUID bandId) {
        Band band = bandService.findById(bandId);

        Comment newComment = Comment.builder()
                .author(author)
                .content(comment.getContent())
                .band(band)
                .createdOn(LocalDateTime.now())
                .build();
        commentRepository.save(newComment);
    }

    public List<Comment> allComments(UUID bandId) {
        return commentRepository.findByBand_IdOrderByCreatedOn(bandId);
    }

    public void setCommentToDelete(UUID id) {
        Comment comment = getCommentById(id);
        comment.setDeleted(true);
        commentRepository.save(comment);
    }

    public Comment getCommentById(UUID id) {
        return commentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Comment not found"));
    }

    public UUID getBandIdByCommentId(UUID id) {
        Comment comment = getCommentById(id);
        return comment.getBand().getId();
    }

    public List<Comment> getNonDeletedCommentsByUser(UUID userId) {
        return commentRepository.findByAuthor_IdAndDeletedIs(userId, false);
    }

    public void switchReportComment(UUID id) {
        Comment comment = getCommentById(id);

        comment.setReported(!comment.isReported());
        commentRepository.save(comment);
    }

    public List<Comment> getReportedComments() {
        return commentRepository.getAllByReported(true);
    }

    public Map<UUID, Integer> getDeletedComments() {

        List<User> allUsers = userService.getAllUsers();
        Map<UUID, Integer> deletedCommentsCount = new HashMap<>();
        for (User user : allUsers) {
            UUID id = user.getId();
            int count = commentRepository.getAllByAuthor_IdAndDeleted(id, true).size();
            deletedCommentsCount.put(id, count);
        }
        return deletedCommentsCount;
    }
}

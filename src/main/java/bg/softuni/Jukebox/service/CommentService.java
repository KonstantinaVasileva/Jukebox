package bg.softuni.Jukebox.service;

import bg.softuni.Jukebox.model.dto.CommentForm;
import bg.softuni.Jukebox.model.entity.Band;
import bg.softuni.Jukebox.model.entity.Comment;
import bg.softuni.Jukebox.model.entity.User;
import bg.softuni.Jukebox.repository.CommentRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class CommentService {


    private final CommentRepository commentRepository;
    private final CurrentUser currentUser;
    private final UserService userService;
    private final BandService bandService;

    public CommentService(CommentRepository commentRepository, CurrentUser currentUser, UserService userService, BandService bandService) {
        this.commentRepository = commentRepository;
        this.currentUser = currentUser;
        this.userService = userService;
        this.bandService = bandService;
    }

    public void addComment(CommentForm comment, UUID bandId) {
        User author = userService.findUserById(currentUser.getId());
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
        return commentRepository.findByBand_Id(bandId);
    }
}

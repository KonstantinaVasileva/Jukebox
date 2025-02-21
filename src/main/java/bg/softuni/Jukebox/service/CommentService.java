package bg.softuni.Jukebox.service;

import bg.softuni.Jukebox.model.dto.CommentForm;
import bg.softuni.Jukebox.model.entity.Band;
import bg.softuni.Jukebox.model.entity.Comment;
import bg.softuni.Jukebox.model.entity.User;
import bg.softuni.Jukebox.repository.CommentRepository;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class CommentService {


    private final CommentRepository commentRepository;
    private final UserService userService;
    private final BandService bandService;

    public CommentService(CommentRepository commentRepository, UserService userService, BandService bandService) {
        this.commentRepository = commentRepository;
        this.userService = userService;
        this.bandService = bandService;
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
        return commentRepository.findByBand_Id(bandId);
    }
}

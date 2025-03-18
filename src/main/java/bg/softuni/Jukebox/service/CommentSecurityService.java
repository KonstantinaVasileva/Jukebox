package bg.softuni.Jukebox.service;

import bg.softuni.Jukebox.model.entity.Comment;
import bg.softuni.Jukebox.repository.CommentRepository;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CommentSecurityService {

    private final CommentRepository commentRepository;

    public CommentSecurityService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    public boolean isOwnerOrAdmin(UUID id, Authentication authentication) {
        String name = authentication.getName();
        Comment comment = commentRepository.findById(id).orElseThrow(() -> new RuntimeException("Comment not found"));
        return comment.getAuthor().getUsername().equals(name)
                || authentication.getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN"));
    }
}

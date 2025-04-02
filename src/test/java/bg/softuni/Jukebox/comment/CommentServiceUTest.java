package bg.softuni.Jukebox.comment;

import bg.softuni.Jukebox.band.Band;
import bg.softuni.Jukebox.band.BandService;
import bg.softuni.Jukebox.user.User;
import bg.softuni.Jukebox.user.UserService;
import bg.softuni.Jukebox.web.dto.CommentForm;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class CommentServiceUTest {

    @Mock
    private CommentRepository commentRepository;

    @Mock
    private BandService bandService;

    @Mock
    private UserService userService;

    @InjectMocks
    private CommentService commentService;

    @Test
    void successfullyAddComment() {
        CommentForm commentForm = CommentForm.builder()
                .content("This is a comment")
                .build();

        Comment comment = Comment.builder()
                .author(new User())
                .content(commentForm.getContent())
                .band(new Band())
                .createdOn(LocalDateTime.now())
                .build();

        when(commentRepository.save(any(Comment.class))).thenReturn(comment);
        when(commentRepository.findAll()).thenReturn(List.of(comment));

        commentService.addComment(new User(), commentForm, UUID.randomUUID());

        assertEquals(1, commentRepository.findAll().size());
    }

    @Test
    void successfullyGetAllComment() {

        Band band = new Band();
        band.setId(UUID.randomUUID());

        List<Comment> comments = commentRepository.findAll();
        assertNotNull(comments);
        assertEquals(comments.size(), commentService.allComments(band.getId()).size());
        when(commentRepository.findAll()).thenReturn(comments);
    }

    @Test
    void successfullyDeleteComment() {
        Comment comment = new Comment();
        comment.setId(UUID.randomUUID());

        when(commentRepository.findById(comment.getId())).thenReturn(Optional.of(comment));

        commentService.setCommentToDelete(comment.getId());
        assertTrue(comment.isDeleted());
    }

    @Test
    void throwExceptionIfCommentNotFound() {
        UUID id = UUID.randomUUID();
        when(commentRepository.getCommentsById(id)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> commentService.getCommentById(id));

        assertEquals("Comment not found", exception.getMessage());
    }

    @Test
    void successfullyGetBandId() {
        Comment comment = new Comment();
        comment.setId(UUID.randomUUID());
        Band band = new Band();
        band.setId(UUID.randomUUID());
        comment.setBand(band);

        when(commentRepository.findById(comment.getId())).thenReturn(Optional.of(comment));

        UUID bandId = commentService.getBandIdByCommentId(comment.getId());
        assertEquals(comment.getBand().getId(), bandId);
    }

    @Test
    void successfullyGetNotDeleteComment() {
        UUID authorId = UUID.randomUUID();
        Comment comment = new Comment();
        comment.setId(UUID.randomUUID());

        when(commentRepository.findByAuthor_IdAndDeletedIs(authorId, false)).thenReturn(List.of(comment));

        commentService.getNonDeletedCommentsByUser(comment.getId());

        assertFalse(comment.isDeleted());
    }

    @ParameterizedTest
    @MethodSource("ReportStatus")
    void successfullySwitchReport(boolean currentStatus, boolean newStatus) {
        Comment comment = new Comment();
        comment.setId(UUID.randomUUID());
        comment.setReported(currentStatus);

        when(commentRepository.findById(comment.getId())).thenReturn(Optional.of(comment));

        commentService.switchReportComment(comment.getId());

        assertEquals(newStatus, comment.isReported());
    }

    private static Stream<Arguments> ReportStatus() {
        return Stream.of(
                Arguments.of(true, false),
                Arguments.of(false, true)
        );
    }

    @Test
    void successfullyGetReportedComments() {
        List<Comment> comments = List.of(new Comment());

        when(commentRepository.getAllByReported(true)).thenReturn(comments);

        assertEquals(comments, commentService.getReportedComments());
    }

    @Test
    void successfullyGetDeletedCommentsByUser() {
        UUID authorId = UUID.randomUUID();
        User user = User.builder()
                .id(authorId)
                .build();
        Map<UUID, Integer> deletedComments = Map.of(authorId, 1);

        List<Comment> comments = List.of(new Comment());

        when(commentRepository.getAllByAuthor_IdAndDeleted(authorId, true)).thenReturn(comments);
        when(userService.getAllUsers()).thenReturn(List.of(user));

        assertEquals(deletedComments, commentService.getDeletedComments());
    }

}

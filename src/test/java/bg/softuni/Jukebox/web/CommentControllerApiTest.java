package bg.softuni.Jukebox.web;

import bg.softuni.Jukebox.band.Band;
import bg.softuni.Jukebox.band.BandService;
import bg.softuni.Jukebox.comment.Comment;
import bg.softuni.Jukebox.comment.CommentService;
import bg.softuni.Jukebox.security.AuthenticationMetadata;
import bg.softuni.Jukebox.user.Role;
import bg.softuni.Jukebox.user.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CommentController.class)
public class CommentControllerApiTest {

    @MockitoBean
    private CommentService commentService;

    @MockitoBean
    private BandService bandService;

    @MockitoBean
    private UserService userService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void getAddCommentEndpoint_returnsAddCommentForm() throws Exception {

        Band band = new Band();
        band.setId(UUID.randomUUID());

        when(bandService.findById(any(UUID.class))).thenReturn(band);

        AuthenticationMetadata principal = new AuthenticationMetadata(UUID.randomUUID(), "username", "password", Role.USER, false);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/comments/add/{id}", band.getId())
                .with(user(principal))
                .with(csrf());

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("band"))
                .andExpect(model().attributeExists("addComment"))
                .andExpect(view().name("comment-add"));
    }

    @Test
    void postAddCommentEndpoint_happyCase() throws Exception {

        Band band = new Band();
        band.setId(UUID.randomUUID());

        when(bandService.findById(any(UUID.class))).thenReturn(band);

        AuthenticationMetadata principal = new AuthenticationMetadata(UUID.randomUUID(), "username", "password", Role.USER, false);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post("/comments/add/{id}", band.getId())
                .formField("content", "This is a comment")
                .with(user(principal))
                .with(csrf());

        mockMvc.perform(request)
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/comments/" + band.getId()));
    }

    @Test
    void postAddCommentEndpointWithInvalidParameter_returnToAddComment() throws Exception {

        Band band = new Band();
        band.setId(UUID.randomUUID());

        when(bandService.findById(any(UUID.class))).thenReturn(band);

        AuthenticationMetadata principal = new AuthenticationMetadata(UUID.randomUUID(), "username", "password", Role.USER, false);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post("/comments/add/{id}", band.getId())
                .formField("content", "")
                .with(user(principal))
                .with(csrf());

        mockMvc.perform(request)
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/comments/add/" + band.getId()));
    }

    @Test
    void getGetCommentsEndpoint_returnsAllComment() throws Exception {
        Band band = new Band();
        band.setId(UUID.randomUUID());
        when(bandService.findById(any(UUID.class))).thenReturn(band);
        AuthenticationMetadata principal = new AuthenticationMetadata(UUID.randomUUID(), "username", "password", Role.USER, false);
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/comments/{id}", band.getId())
                .with(user(principal))
                .with(csrf());
        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("band"))
                .andExpect(model().attributeExists("comments"))
                .andExpect(view().name("comments-all"));
    }

    @Test
    void deleteCommentWhenAuthorized() throws Exception {
        Band band = new Band();
        band.setId(UUID.randomUUID());
        Comment comment = new Comment();
        comment.setId(UUID.randomUUID());


        when(commentService.getBandIdByCommentId(any(UUID.class))).thenReturn(band.getId());

        AuthenticationMetadata principal = new AuthenticationMetadata(UUID.randomUUID(), "username", "password", Role.ADMIN, false);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/comments/delete/{id}", comment.getId())
                .with(user(principal))
                .with(csrf());

        mockMvc.perform(request)
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/comments/" + band.getId()));
    }

    @Test
    void getReportCommentEndpoint_returnsAllComment() throws Exception {
        Band band = new Band();
        band.setId(UUID.randomUUID());
        Comment comment = new Comment();
        comment.setId(UUID.randomUUID());

        when(commentService.getBandIdByCommentId(any(UUID.class))).thenReturn(band.getId());

        AuthenticationMetadata principal = new AuthenticationMetadata(UUID.randomUUID(), "username", "password", Role.MODERATOR, false);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/comments/report/{id}", comment.getId())
                .with(user(principal))
                .with(csrf());

        when(commentService.getCommentById(any(UUID.class))).thenReturn(comment);

        mockMvc.perform(request)
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/comments/" + band.getId()));

        verify(commentService, times(1)).switchReportComment(any(UUID.class));
    }

    @Test
    void getAllReportCommentEndpoint_returnsAllReportedComment() throws Exception {
        List<Comment> reportedComments = List.of(new Comment());
        when(commentService.getReportedComments()).thenReturn(reportedComments);
        AuthenticationMetadata principal = new AuthenticationMetadata(UUID.randomUUID(), "username", "password", Role.ADMIN, false);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/comments/report/all")
                .with(user(principal))
                .with(csrf());

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("reportedComments"))
                .andExpect(view().name("reported-comments"));
    }

    @Test
    void getUnreportCommentEndpoint_returnsAllReportedComment() throws Exception {
        Band band = new Band();
        band.setId(UUID.randomUUID());
        Comment comment = new Comment();
        comment.setId(UUID.randomUUID());

        when(commentService.getBandIdByCommentId(any(UUID.class))).thenReturn(band.getId());

        AuthenticationMetadata principal = new AuthenticationMetadata(UUID.randomUUID(), "username", "password", Role.ADMIN, false);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/comments/unreport/{id}", comment.getId())
                .with(user(principal))
                .with(csrf());

        when(commentService.getCommentById(any(UUID.class))).thenReturn(comment);

        mockMvc.perform(request)
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/comments/report/all"));

        verify(commentService, times(1)).switchReportComment(any(UUID.class));
    }
}

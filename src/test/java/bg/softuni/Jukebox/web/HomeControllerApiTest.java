package bg.softuni.Jukebox.web;

import bg.softuni.Jukebox.comment.Comment;
import bg.softuni.Jukebox.comment.CommentService;
import bg.softuni.Jukebox.notification.service.NotificationService;
import bg.softuni.Jukebox.security.AuthenticationMetadata;
import bg.softuni.Jukebox.user.Role;
import bg.softuni.Jukebox.user.User;
import bg.softuni.Jukebox.user.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;


import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(HomeController.class)
public class HomeControllerApiTest {

    @MockitoBean
    private UserService userService;

    @MockitoBean
    private CommentService commentService;

    @MockitoBean
    private NotificationService notificationService;

    @Autowired
    private MockMvc mvc;
    @Autowired
    private MockMvc mockMvc;

    @Test
    void getRequestToIndexEndpoint_ReturnsIndexPage() throws Exception {
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/");
        mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(view().name("index"));
    }

    @Test
    void getRequestToHomeEndpoint_ReturnsHomePage() throws Exception {

        when(userService.findById(any())).thenReturn(User.builder()
                .id(UUID.randomUUID())
                .email("email@email.com")
                .username("username")
                .password("password")
                .role(Role.USER)
                .build());

        AuthenticationMetadata principal = new AuthenticationMetadata(UUID.randomUUID(),
                "username",
                "password",
                Role.USER,
                false);

        List<Comment> comments = List.of(Comment.builder().content("comment").build());

        when(commentService.getNonDeletedCommentsByUser(principal.getId())).thenReturn(comments);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/home")
                .with(user(principal));

        mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(view().name("home"))
                .andExpect(model().attributeExists("user"))
                .andExpect(model().attributeExists("nonDeletedCommentsByUser"));

        verify(userService, times(1)).findById(principal.getId());
    }

    @Test
    void getUnauthenticatedHomeEndpoint_ReturnsLoginPage() throws Exception {
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/home");
        mvc.perform(request)
                .andExpect(status().is3xxRedirection());
        verify(userService, times(0)).findById(any());
    }

    @Test
    void getRequestToRegisterEndpoint_ReturnsRegisterPage() throws Exception {
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/register");
        mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(view().name("register"));
    }

    @Test
    void postRequestToRegisterEndpoint_happyCase() throws Exception {
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post("/register")
                .formField("username", "username")
                .formField("password", "password")
                .formField("repeatPassword", "password")
                .formField("email", "email@email.com")
                .formField("role", "USER")
                .with(csrf());

        mockMvc.perform(request)
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login"));

        verify(userService, times(1)).validateUserRegistration(any());
    }

    @Test
    void postRequestWithInvalidParameterToRegisterEndpoint_returnRegister() throws Exception {
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post("/register")
                .formField("username", "u")
                .formField("password", "p")
                .formField("repeatPassword", "p")
                .formField("email", "email")
                .formField("role", "USER")
                .with(csrf());
        mockMvc.perform(request)
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/register"));
        verify(notificationService, times(0)).sendWelcomeNotification(any());
    }

    @Test
    void getRequestToLoginEndpoint_ReturnsLoginPage() throws Exception {
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/login");
        mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(view().name("login"))
        .andExpect(model().attributeExists("loginUserRequest"));
    }

    @Test
    void getRequestToLoginEndpoint_returnLogin() throws Exception {
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/login")
                        .param("error", "invalid");
        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(view().name("login"))
                .andExpect(model().attributeExists("errorMessage"));
    }

    @Test
    void getTermsAndPrivacyEndpoint_ReturnsTermsAndPrivacyPage() throws Exception {
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/register/terms-and-privacy");
        mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(view().name("terms-and-conditions"));
    }
}

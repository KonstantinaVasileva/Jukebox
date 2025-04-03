package bg.softuni.Jukebox.web;

import bg.softuni.Jukebox.comment.CommentService;
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

import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
public class UserControllerApiTest {

    @MockitoBean
    private UserService userService;

    @MockitoBean
    private CommentService commentService;

    @Autowired
    private MockMvc mvc;

    @Test
    void getAllUsersEndpoint_returnsAllUsers() throws Exception {

        AuthenticationMetadata principal = new AuthenticationMetadata(UUID.randomUUID(),
                "username",
                "password",
                Role.ADMIN,
                false);
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/users/all")
                .with(user(principal))
                .with(csrf());

        mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("users"))
                .andExpect(model().attributeExists("deletedComments"))
                .andExpect(view().name("users-all"));
    }

    @Test
    void getSwitchUserBanEndpoint_returnsUsersAll() throws Exception {
        User user = new User();
        user.setId(UUID.randomUUID());
        user.setRole(Role.USER);

        AuthenticationMetadata principal = new AuthenticationMetadata(UUID.randomUUID(),
                "username",
                "password",
                Role.ADMIN,
                false);

        when(userService.findById(user.getId())).thenReturn(user);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post("/users/{id}/ban", user.getId())
                .with(user(principal))
                .with(csrf());

        mvc.perform(request)
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/users/all"));
        verify(userService, times(1)).switchBan(user.getId());

    }

    @Test
    void postSwitchUserRoleEndpoint_returnsUsersAll() throws Exception {
        User user = new User();
        user.setId(UUID.randomUUID());
        user.setRole(Role.USER);

        AuthenticationMetadata principal = new AuthenticationMetadata(UUID.randomUUID(),
                "username",
                "password",
                Role.ADMIN,
                false);

        when(userService.findById(user.getId())).thenReturn(user);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post("/users/{id}/role", user.getId())
                .with(user(principal))
                .with(csrf());
        mvc.perform(request)
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/users/all"));
    }

    @Test
    void deleteUserEndpoint_returnsUser() throws Exception {
        User user = new User();
        user.setId(UUID.randomUUID());
        user.setRole(Role.USER);

        AuthenticationMetadata principal = new AuthenticationMetadata(UUID.randomUUID(),
                "username",
                "password",
                Role.ADMIN,
                false);
        when(userService.findById(user.getId())).thenReturn(user);
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.delete("/users/{id}/delete", user.getId())
                .with(user(principal))
                .with(csrf());
        mvc.perform(request)
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/users/all"));

    }
}

package bg.softuni.Jukebox.user;

import bg.softuni.Jukebox.exception.UserAlreadyExistsException;
import bg.softuni.Jukebox.notification.service.NotificationService;
import bg.softuni.Jukebox.web.dto.RegisterUserRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceUTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private NotificationService notificationService;

    @InjectMocks
    private UserService userService;

    @Test
    void whenTryToRegisterWithUsernameOrEmailThatExists_thenThrowException() {
        RegisterUserRequest registerUserRequest = RegisterUserRequest.builder()
                .username("username")
                .email("email")
                .build();

        User user = User.builder()
                .username("username")
                .email("email")
                .build();

        when(userRepository.findByUsernameOrEmail(any(), any())).thenReturn(user);

        UserAlreadyExistsException exception = assertThrows(UserAlreadyExistsException.class, () -> userService.validateUserRegistration(registerUserRequest));
        assertEquals("Username is in use, choose another one!", exception.getMessage());
    }

    @Test
    void happyCaseWhenTryToRegisterWhitUnusedUsernameOrEmail() {
        RegisterUserRequest registerUserRequest = RegisterUserRequest.builder()
                .username("username")
                .email("email")
                .build();

        assertNull(userRepository.findByUsernameOrEmail(registerUserRequest.getUsername(), registerUserRequest.getEmail()));
    }

    @Test
    void happyCaseWhenRegisterUser(){
        RegisterUserRequest registerUserRequest = RegisterUserRequest.builder()
                .username("username")
                .email("email@example.com")
                .build();

        User user = User.builder()
                .username(registerUserRequest.getUsername())
                .email(registerUserRequest.getEmail())
                .build();

        when(userRepository.save(any(User.class))).thenReturn(user);

        User register = userService.register(registerUserRequest);

        assertNotNull(register);
        assertEquals(registerUserRequest.getUsername(), register.getUsername());
        assertEquals(registerUserRequest.getEmail(), register.getEmail());

    }

    @Test
    void returnUserWhenUserExists() {
        User user = User.builder()
                .username("username")
                .build();

        when(userRepository.findByUsername(any())).thenReturn(Optional.of(user));

        assertNotNull(userService.findByUsername(user.getUsername()));
        assertEquals(user.getUsername(), userService.findByUsername(user.getUsername()).getUsername());
    }
}

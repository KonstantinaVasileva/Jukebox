package bg.softuni.Jukebox.user;

import bg.softuni.Jukebox.exception.UserAlreadyExistsException;
import bg.softuni.Jukebox.notification.service.NotificationService;
import bg.softuni.Jukebox.web.dto.RegisterUserRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceUTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

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
    void happyCaseWhenRegisterUser() {
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

    @Test
    void throwExceptionWhenUserDoesNotExist() {
        User user = User.builder()
                .username("username")
                .build();

        when(userRepository.findByUsername(any())).thenReturn(Optional.empty());

        UsernameNotFoundException exception = assertThrows(UsernameNotFoundException.class, () -> userService.findByUsername(user.getUsername()));
        assertEquals("User with username username not found!", exception.getMessage());
    }

    @ParameterizedTest
    @MethodSource("banStatus")
    void changeStatusWhenSwitchBan(boolean currentStatus, boolean newStatus) {
        UUID userId = UUID.randomUUID();
        User user = User.builder()
                .banned(currentStatus)
                .build();
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        userService.switchBan(userId);

        assertEquals(newStatus, user.isBanned());
    }

    private static Stream<Arguments> banStatus() {

        return Stream.of(
                Arguments.of(true, false),
                Arguments.of(false, true)
        );
    }

    @Test
    void happyCaseWhenFindUserById() {
        User user = User.builder()
                .id(UUID.randomUUID())
                .build();

        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        assertNotNull(userService.findById(user.getId()));
        assertEquals(user.getUsername(), userService.findById(user.getId()).getUsername());
    }

    @Test
    void throwExceptionWhenNoFindUserById() {
        UUID id = UUID.randomUUID();
        when(userRepository.findById(id)).thenReturn(Optional.empty());
        UsernameNotFoundException exception = assertThrows(UsernameNotFoundException.class, () -> userService.findById(id));
        assertEquals("User with id " + id + " not found!", exception.getMessage());
    }

    @Test
    void happyCaseWhenGetAllUsers() {
        List<User> users = userRepository.findAll();
        assertNotNull(users);
        assertEquals(users.size(), userService.getAllUsers().size());
    }

    @Test
    void throwExceptionWhenTryToDeleteUsers() {
        User user = User.builder()
                .id(UUID.randomUUID())
                .build();
        when(userRepository.findById(user.getId())).thenReturn(Optional.empty());
        UsernameNotFoundException exception = assertThrows(UsernameNotFoundException.class, () -> userService.deleteUser(user.getId()));
        assertEquals("User with id " + user.getId() + " not found!", exception.getMessage());
    }

    @Test
    void happyCaseWhenDeleteUsers() {
        User user = User.builder()
                .id(UUID.randomUUID())
                .build();

        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));

        userService.deleteUser(user.getId());

        when(userRepository.findById(user.getId())).thenReturn(Optional.empty());

        UsernameNotFoundException exception = assertThrows(UsernameNotFoundException.class, () -> userService.findById(user.getId()));
        assertEquals("User with id " + user.getId() + " not found!", exception.getMessage());
    }
}

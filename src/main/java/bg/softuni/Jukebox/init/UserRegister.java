package bg.softuni.Jukebox.init;

import bg.softuni.Jukebox.user.Role;
import bg.softuni.Jukebox.user.UserRepository;
import bg.softuni.Jukebox.user.UserService;
import bg.softuni.Jukebox.web.dto.RegisterUserRequest;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class UserRegister implements CommandLineRunner {
    private final UserRepository userRepository;
    private final UserService userService;

    public UserRegister(UserRepository userRepository, UserService userService) {
        this.userRepository = userRepository;
        this.userService = userService;
    }

    @Override
    public void run(String... args) throws Exception {

        if(userRepository.count() == 0) {
            registerData("admin", "admin", "admin@admin", Role.ADMIN);
            registerData("moderator", "moderator", "moderator@moderator", Role.MODERATOR);
            registerData("artist", "artist", "artist@artist", Role.ARTIST);
            registerData("user", "user", "user@user", Role.USER);
        }

    }

    private void registerData(String username, String password, String email, Role role) {
        RegisterUserRequest registerUserRequest = RegisterUserRequest.builder()
                .username(username)
                .password(password)
                .email(email)
                .role(role)
                .build();

        userService.register(registerUserRequest);
    }
}

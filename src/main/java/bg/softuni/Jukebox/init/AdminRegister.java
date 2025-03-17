package bg.softuni.Jukebox.init;

import bg.softuni.Jukebox.model.entity.Role;
import bg.softuni.Jukebox.repository.UserRepository;
import bg.softuni.Jukebox.service.UserService;
import bg.softuni.Jukebox.web.dto.RegisterUserRequest;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class AdminRegister implements CommandLineRunner {
    private final UserRepository userRepository;
    private final UserService userService;

    public AdminRegister(UserRepository userRepository, UserService userService) {
        this.userRepository = userRepository;
        this.userService = userService;
    }

    @Override
    public void run(String... args) throws Exception {

        if(userRepository.count() == 0) {
            RegisterUserRequest registerUserRequest = RegisterUserRequest.builder()
                    .username("admin")
                    .password("admin")
                    .email("admin@admin.com")
                    .role(Role.ADMIN)
                    .build();

            userService.register(registerUserRequest);
        }

    }
}

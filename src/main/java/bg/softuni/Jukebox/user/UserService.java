package bg.softuni.Jukebox.user;

import bg.softuni.Jukebox.exception.UserAlreadyExistsException;
import bg.softuni.Jukebox.web.dto.RegisterUserRequest;
import bg.softuni.Jukebox.security.AuthenticationMetadata;
import bg.softuni.Jukebox.web.dto.SwitchUserRole;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void validateUserRegistration(RegisterUserRequest registerUserRequest) {

        User byUsernameOrEmail = userRepository.findByUsernameOrEmail(registerUserRequest.getUsername(), registerUserRequest.getEmail());
        if (byUsernameOrEmail != null) {
            throw new UserAlreadyExistsException("Username is in use, choose another one!");
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User with this username dose not exist!"));
        return new AuthenticationMetadata(user.getId(), username, user.getPassword(), user.getRole(), user.isBanned());
    }

    public User register(RegisterUserRequest registerUserRequest) {

        User user = User.builder()
                .username(registerUserRequest.getUsername())
                .email(registerUserRequest.getEmail())
                .password(passwordEncoder.encode(registerUserRequest.getPassword()))
                .role(registerUserRequest.getRole())
                .build();

        if (user.getRole() == null) {
            user.setRole(Role.USER);
        }

        User saved = userRepository.save(user);
        return saved;
    }

    public User findByUsername(String username) {

        return userRepository.findByUsername(username).orElseThrow(() ->
                new UsernameNotFoundException("User with username " + username + " not found!"));
    }

    public void switchBan(UUID id) {

        User byId = findById(id);
        byId.setBanned(!byId.isBanned());
        userRepository.save(byId);
    }

    public User findById(UUID id) {

        return userRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException("User with id " + id + " not found!"));
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public void switchRole(UUID id, SwitchUserRole switchUserRole) {

        User byId = findById(id);
        byId.setRole(switchUserRole.getRole());
        userRepository.save(byId);
    }

    public void deleteUser(UUID id) {
        User user = findById(id);
        userRepository.delete(user);
    }
}

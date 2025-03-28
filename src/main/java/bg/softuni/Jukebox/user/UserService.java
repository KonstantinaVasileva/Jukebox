package bg.softuni.Jukebox.user;

import bg.softuni.Jukebox.exception.UserAlreadyExistsException;
import bg.softuni.Jukebox.notification.client.dto.Notification;
import bg.softuni.Jukebox.notification.service.NotificationService;
import bg.softuni.Jukebox.web.dto.RegisterUserRequest;
import bg.softuni.Jukebox.security.AuthenticationMetadata;
import bg.softuni.Jukebox.web.dto.SwitchUserRole;
import org.modelmapper.ModelMapper;
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
    private final ModelMapper modelMapper;
    private final NotificationService notificationService;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, ModelMapper modelMapper, NotificationService notificationService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.modelMapper = modelMapper;
        this.notificationService = notificationService;
    }

    public void validateUserRegistration(RegisterUserRequest registerUserRequest) {

        User byUsernameOrEmail = userRepository.findByUsernameOrEmail(registerUserRequest.getUsername(), registerUserRequest.getEmail());
        if (byUsernameOrEmail != null) {
            throw new UserAlreadyExistsException("Username is in used, choose another one!");
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User with this username dose not exist!"));
        return new AuthenticationMetadata(user.getId(), username, user.getPassword(), user.getRole(), user.isBanned());
    }

    public Notification register(RegisterUserRequest registerUserRequest) {

        User user = modelMapper.map(registerUserRequest, User.class);
        user.setPassword(passwordEncoder.encode(registerUserRequest.getPassword()));

        if (user.getRole() == null) {
            user.setRole(Role.USER);
        }

        userRepository.save(user);

        return notificationService.sendWelcomeNotification(registerUserRequest);
    }

    public User findByUsername(String username) {

        return userRepository.findByUsername(username).orElseThrow(() ->
                new UsernameNotFoundException("User with username " + username + " not found!"));
    }

    public void switchBan(UUID id) {

        User byId = userRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException("User with id " + id + " not found!"));
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

        User byId = userRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException("User with id " + id + " not found!"));
        byId.setRole(switchUserRole.getRole());
        userRepository.save(byId);
    }

    public void deleteUser(UUID id) {
        User user = userRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException("User with id " + id + " not found!"));
        userRepository.delete(user);
    }
}

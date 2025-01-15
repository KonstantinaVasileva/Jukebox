package bg.softuni.Jukebox.service;

import bg.softuni.Jukebox.model.dto.RegisterUserRequest;
import bg.softuni.Jukebox.model.entity.User;
import bg.softuni.Jukebox.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.modelMapper = modelMapper;
    }

    public boolean login(String username, String password) {
        User user = userRepository.findByUsername(username);
        if (user == null || passwordEncoder.matches(password, user.getPassword())) {
            return false;
        }
        return true;
    }

    public boolean validateUserRegistration(RegisterUserRequest registerUserRequest) {
        User byUsernameOrEmail = userRepository.findByUsernameOrEmail(registerUserRequest.getUsername(), registerUserRequest.getEmail());
        if (byUsernameOrEmail != null) {
            return false;
        }
        return true;
    }

    public void register(RegisterUserRequest registerUserRequest) {
        User user = modelMapper.map(registerUserRequest, User.class);
        user.setPassword(passwordEncoder.encode(registerUserRequest.getPassword()));
        userRepository.save(user);
    }
}

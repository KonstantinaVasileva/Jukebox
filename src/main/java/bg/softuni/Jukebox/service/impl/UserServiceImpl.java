package bg.softuni.Jukebox.service.impl;

import bg.softuni.Jukebox.model.entity.User;
import bg.softuni.Jukebox.repository.UserRepository;
import bg.softuni.Jukebox.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Boolean login(String username, String password) {
        User user = userRepository.findByUsername(username);
        if (user == null || passwordEncoder.matches(password, user.getPassword())) {
            return false;
        }
        return true;
    }
}

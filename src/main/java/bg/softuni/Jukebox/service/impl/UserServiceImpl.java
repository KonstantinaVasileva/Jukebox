package bg.softuni.Jukebox.service.impl;

import bg.softuni.Jukebox.model.dto.RegisterUserDTO;
import bg.softuni.Jukebox.model.entity.User;
import bg.softuni.Jukebox.repository.UserRepository;
import bg.softuni.Jukebox.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.modelMapper = modelMapper;
    }

    @Override
    public boolean login(String username, String password) {
        User user = userRepository.findByUsername(username);
        if (user == null || passwordEncoder.matches(password, user.getPassword())) {
            return false;
        }
        return true;
    }

    @Override
    public boolean validateUserRegistration(RegisterUserDTO registerUserDTO) {
        User byUsernameOrEmail = userRepository.findByUsernameOrEmail(registerUserDTO.getUsername(), registerUserDTO.getEmail());
        if (byUsernameOrEmail == null) {
            return false;
        }
        return true;
    }

    @Override
    public void register(RegisterUserDTO registerUserDTO) {
        User user = modelMapper.map(registerUserDTO, User.class);
        user.setPassword(passwordEncoder.encode(registerUserDTO.getPassword()));
        userRepository.save(user);
    }
}

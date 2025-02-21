package bg.softuni.Jukebox.service;

import bg.softuni.Jukebox.model.dto.RegisterUserRequest;
import bg.softuni.Jukebox.model.entity.User;
import bg.softuni.Jukebox.repository.UserRepository;
import bg.softuni.Jukebox.security.AuthenticationMetadata;
import org.hibernate.sql.results.DomainResultCreationException;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new DomainResultCreationException("User with this username dose not exist!"));
        return new AuthenticationMetadata(user.getId(), username, user.getPassword());
    }

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.modelMapper = modelMapper;
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

    public User findByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User with username " + username + " not found!"));
    }
}

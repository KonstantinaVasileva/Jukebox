package bg.softuni.Jukebox.service;

import bg.softuni.Jukebox.model.dto.RegisterUserDTO;
import jakarta.validation.Valid;

public interface UserService {
    boolean login(String username, String password);

    boolean validateUserRegistration(RegisterUserDTO registerUserDTO);

    void register(RegisterUserDTO registerUserDTO);
}

package bg.softuni.Jukebox.web.dto;

import bg.softuni.Jukebox.user.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterUserRequest {
    @NotBlank
    @Size(min = 3, max = 20, message = "Username must between 3 and 20 symbols!")
    private String username;
    @NotBlank
    @Email(message = "Not valid email!")
    private String email;
    private Role role;
    @NotBlank
    //@Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,}$",
    //message = "Password must content minimum eight characters, at least one letter, one number and one special character!")
    private String password;
    private String repeatPassword;

}

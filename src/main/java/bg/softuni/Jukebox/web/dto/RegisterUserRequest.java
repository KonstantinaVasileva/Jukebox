package bg.softuni.Jukebox.web.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RegisterUserRequest {
    @NotBlank
    @Size(min = 3, max = 20, message = "Username must between 3 and 20 symbols!")
    private String username;
    @NotBlank
    @Email(message = "Not valid email!")
    private String email;
    @NotBlank
    //@Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,}$",
    //message = "Password must content minimum eight characters, at least one letter, one number and one special character!")
    private String password;
    private String repeatPassword;

}

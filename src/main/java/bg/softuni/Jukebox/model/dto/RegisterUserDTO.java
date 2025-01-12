package bg.softuni.Jukebox.model.dto;

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
public class RegisterUserDTO {
    @NotBlank
    @Size(min = 3, max = 20, message = "{register.user.username.size}")
    private String username;
    @NotBlank
    @Email(message = "{register.user.email.validation}")
    private String email;
    @NotBlank
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,}$",
    message = "{register.user.password.pattern}")
    private String password;
    private String repeatPassword;

}

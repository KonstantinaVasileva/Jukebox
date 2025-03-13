package bg.softuni.Jukebox.web;

import bg.softuni.Jukebox.exeption.UserAlreadyExistsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@ControllerAdvice
public class ExceptionAdvised {

    @ExceptionHandler(UserAlreadyExistsException.class)
    public String handleUserAlreadyExistsException(RedirectAttributes redirectAttributes,
                                                   UserAlreadyExistsException userAlreadyExistsException) {
        redirectAttributes.addFlashAttribute("userAlreadyExistsException", userAlreadyExistsException.getMessage());
        return "redirect:/register";

    }
}

package bg.softuni.Jukebox.web;

import bg.softuni.Jukebox.exception.UserAlreadyExistsException;
import bg.softuni.Jukebox.exception.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({UserAlreadyExistsException.class})
    public String handleUserAlreadyExistsException(RedirectAttributes redirectAttributes,
                                                   UserAlreadyExistsException userAlreadyExistsException) {
        redirectAttributes.addFlashAttribute("userAlreadyExistsException", userAlreadyExistsException.getMessage());
        return "redirect:/register";

    }

    @ExceptionHandler({UsernameNotFoundException.class})
    public String exceptionHandler(UsernameNotFoundException usernameNotFoundException, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("usernameNotFoundException", usernameNotFoundException.getMessage());
        return "redirect:/global-error";
    }


}

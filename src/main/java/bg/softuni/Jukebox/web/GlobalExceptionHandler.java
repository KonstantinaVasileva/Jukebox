package bg.softuni.Jukebox.web;

import bg.softuni.Jukebox.exception.SearchNotFoundException;
import bg.softuni.Jukebox.exception.UserAlreadyExistsException;
import jakarta.persistence.NoResultException;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.nio.file.AccessDeniedException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({UserAlreadyExistsException.class})
    public String handleUserAlreadyExistsException(RedirectAttributes redirectAttributes,
                                                   UserAlreadyExistsException userAlreadyExistsException) {
        redirectAttributes.addFlashAttribute("userAlreadyExistsException", userAlreadyExistsException.getMessage());
        return "redirect:/register";
    }

    @ExceptionHandler({SearchNotFoundException.class})
    public String handlerSearchNotFoundException(RedirectAttributes redirectAttributes,
                                                 SearchNotFoundException searchNotFoundException) {
        redirectAttributes.addFlashAttribute("searchNotFoundException", searchNotFoundException.getMessage());
        return "redirect:/home";
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler({
            UsernameNotFoundException.class,
            AccessDeniedException.class,
            NoResultException.class})
    public String exceptionHandler(UsernameNotFoundException usernameNotFoundException, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("usernameNotFoundException", usernameNotFoundException.getMessage());
        return "redirect:/global-error";
    }

    @ExceptionHandler({Exception.class})
    public String exceptionHandler(Exception exception, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("exception", exception);
        return "redirect:/error";
    }

}

//package bg.softuni.Jukebox.service;
//
//import lombok.Getter;
//import lombok.Setter;
//import org.springframework.stereotype.Component;
//import org.springframework.web.context.annotation.SessionScope;
//
//import java.util.UUID;
//
//@Component
//@SessionScope
//@Getter
//@Setter
//public class CurrentUser {
//    private UUID id;
//    private String username;
//    private boolean isLoggedIn;
//
//    public boolean isLoggedIn() {
//        return isLoggedIn;
//    }
//
//    public void setLoggedIn(boolean loggedIn) {
//        isLoggedIn = loggedIn;
//    }
//
//    public void logout() {
//        isLoggedIn = false;
//        username = null;
//    }
//
//    public void login(String username) {
//        this.username = username;
//        isLoggedIn = true;
//    }
//}

package bg.softuni.Jukebox.security;

import bg.softuni.Jukebox.model.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
public class AuthenticationMetadata implements UserDetails {

    private UUID id;
    private String username;
    private String password;
    private Role role;
    private boolean banned;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.banned;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return this.banned;
    }

    @Override
    public boolean isEnabled() {
        return this.banned;
    }
}

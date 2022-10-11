package ru.xpressed.databaseapplication.entity;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.util.Collection;

@Data
@Entity
public class User implements UserDetails {
    @Id
    @NotEmpty(message = "Username can not be empty!")
    @Pattern(regexp = "^[a-zA-Z0-9]{3,15}$", message = "Only numbers and at least 3 and not more than 15 letters!")
    private String username;

    @NotEmpty(message = "Password can not be empty!")
    private String password;

    @Transient
    private String repeated;

    @Transient
    private String old;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}

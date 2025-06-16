package com.example.loki.security;

import com.example.loki.model.Perfil;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class UserDetailsImpl implements UserDetails {
    private final Perfil perfil;

    public UserDetailsImpl(Perfil perfil) {
        this.perfil = perfil;
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

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + perfil.getRol().name()));
    }

    @Override
    public String getPassword() {
        return perfil.getPass();
    }

    @Override
    public String getUsername() {
        return perfil.getEmail();
    }
}

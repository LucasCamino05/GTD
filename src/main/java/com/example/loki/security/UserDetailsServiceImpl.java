package com.example.loki.security;

import com.example.loki.model.Perfil;
import com.example.loki.repository.PerfilRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private final PerfilRepository repository;

    @Autowired
    public UserDetailsServiceImpl(PerfilRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException{
        Perfil perfil = repository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("No existe el usuario."));

        return new UserDetailsImpl(perfil);
    }
}

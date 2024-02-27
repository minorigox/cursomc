package com.osprasoft.cursomc.security;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.osprasoft.cursomc.domain.enums.Perfil;

public class UserSS implements UserDetails {

    private Integer id;
    private String email;
    private String senha;
    private Set < Perfil > perfis;
    
    public UserSS(Integer id, String email, String senha, Set < Perfil > perfis) {
        this.id = id;
        this.email = email;
        this.senha = senha;
        this.perfis = perfis;
    }

    public Integer getId() {
        return id;
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (this.perfis.contains(Perfil.ADMIN)) return List.of(
            new SimpleGrantedAuthority("ROLE_ADMIN"), 
            new SimpleGrantedAuthority("ROLE_CLIENTE"));
        else return List.of(new SimpleGrantedAuthority("ROLE_CLIENTE"));
    }

    @Override
    public String getPassword() {
        return senha;
    }
    @Override
    public String getUsername() {
        return email;
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

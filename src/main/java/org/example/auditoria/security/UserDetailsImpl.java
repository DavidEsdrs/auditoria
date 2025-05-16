package org.example.auditoria.security;

import java.util.Collection;

import org.example.auditoria.model.Departamento;
import org.example.auditoria.model.Papel;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class UserDetailsImpl implements UserDetails {
    private Long id;
    private String email;
    private String senha;
    private Collection<? extends GrantedAuthority> authorities;
    private Papel papel;
    private Departamento departamento;

     public UserDetailsImpl(Long id, String email, String senha, Papel papel, Departamento departamento, Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.email = email;
        this.senha = senha;
        this.authorities = authorities;
        this.departamento = departamento;
        this.papel = papel;
    }

    public Long getId() {
        return id;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return senha;
    }

    @Override
    public String getUsername() {
        return email;
    }

    public Papel getPapel() {
        return papel;
    }

    public Departamento getDepartamento() {
        return departamento;
    }

}

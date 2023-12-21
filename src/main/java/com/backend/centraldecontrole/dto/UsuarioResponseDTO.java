package com.backend.centraldecontrole.dto;

import com.backend.centraldecontrole.model.Usuario;

import java.util.Date;

public record UsuarioResponseDTO(Long id, String username, String senha, Date dataInclusao) {
    public UsuarioResponseDTO(Usuario usuario) {
        this(usuario.getId(), usuario.getUsername(), usuario.getSenha(), usuario.getDataInclusao());
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getSenha() {
        return senha;
    }

    public Date getDataInclusao() {
        return dataInclusao;
    }
}

package com.backend.centraldecontrole.dto;

public record UsuarioRequestDTO(String username, String senha) {

    public String getUsername() {
        return username;
    }

    public String getSenha() {
        return senha;
    }
}

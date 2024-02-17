package br.com.lcano.centraldecontrole.dto;

public record UsuarioRequestDTO(String username, String senha) {

    public String getUsername() {
        return username;
    }

    public String getSenha() {
        return senha;
    }
}

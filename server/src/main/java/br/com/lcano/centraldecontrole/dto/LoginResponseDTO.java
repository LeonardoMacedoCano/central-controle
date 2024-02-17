package br.com.lcano.centraldecontrole.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record LoginResponseDTO(String username, String token) {
    @JsonProperty("usuario")
    public UsuarioDTO usuario() {
        return new UsuarioDTO(username, token);
    }

    public record UsuarioDTO(String username, String token) {
    }
}

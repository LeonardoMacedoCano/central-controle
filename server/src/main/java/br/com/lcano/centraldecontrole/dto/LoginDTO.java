package br.com.lcano.centraldecontrole.dto;

import lombok.Data;

@Data
public class LoginDTO {
    private UsuarioLoginDTO usuario;

    public LoginDTO(String username, String token) {
        this.usuario = new UsuarioLoginDTO(username, token);
    }

    @Data
    public static class UsuarioLoginDTO {
        private String username;
        private String token;

        public UsuarioLoginDTO(String username, String token) {
            this.username = username;
            this.token = token;
        }
    }
}

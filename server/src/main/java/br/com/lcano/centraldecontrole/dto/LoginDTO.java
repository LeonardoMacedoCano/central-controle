package br.com.lcano.centraldecontrole.dto;

import lombok.Data;

@Data
public class LoginDTO {
    private UsuarioLoginDTO usuario;

    public LoginDTO(String username, String token, Long idTema) {
        this.usuario = new UsuarioLoginDTO(username, token, idTema);
    }

    @Data
    public static class UsuarioLoginDTO {
        private String username;
        private String token;
        private Long idTema;

        public UsuarioLoginDTO(String username, String token, Long idTema) {
            this.username = username;
            this.token = token;
            this.idTema = idTema;
        }
    }
}

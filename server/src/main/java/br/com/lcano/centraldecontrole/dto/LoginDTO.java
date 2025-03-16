package br.com.lcano.centraldecontrole.dto;

import lombok.Data;

@Data
public class LoginDTO {
    private UsuarioLoginDTO usuario;

    public LoginDTO(String username, String token, Long idTema, Long idArquivo) {
        this.usuario = new UsuarioLoginDTO(username, token, idTema, idArquivo);
    }

    @Data
    public static class UsuarioLoginDTO {
        private String username;
        private String token;
        private Long idTema;
        private Long idArquivo;

        public UsuarioLoginDTO(String username, String token, Long idTema, Long idArquivo) {
            this.username = username;
            this.token = token;
            this.idTema = idTema;
            this.idArquivo = idArquivo;
        }
    }
}

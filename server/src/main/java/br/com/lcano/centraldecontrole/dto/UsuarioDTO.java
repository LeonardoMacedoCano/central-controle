package br.com.lcano.centraldecontrole.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioDTO {
    private Long id;
    private String username;
    private String senha;
    private Date dataInclusao;

    public UsuarioDTO(String username, String senha) {
        this.username = username;
        this.senha = senha;
    }
}

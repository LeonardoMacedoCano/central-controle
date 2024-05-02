package br.com.lcano.centraldecontrole.dto;

import br.com.lcano.centraldecontrole.domain.Usuario;
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

    public UsuarioDTO(Usuario usuario) {
        this(usuario.getId(), usuario.getUsername(), usuario.getSenha(), usuario.getDataInclusao());
    }

    public UsuarioDTO(String username, String senha) {
        this.username = username;
        this.senha = senha;
    }
}

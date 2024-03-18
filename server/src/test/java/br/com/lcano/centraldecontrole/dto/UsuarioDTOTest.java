package br.com.lcano.centraldecontrole.dto;

import br.com.lcano.centraldecontrole.domain.Usuario;
import org.junit.jupiter.api.Test;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class UsuarioDTOTest {

    @Test
    void testConstructorAndGetters() {
        Usuario usuario = new Usuario(1L, "testUser", "testPassword",  new Date(), true);
        UsuarioDTO usuarioDTO = new UsuarioDTO(usuario);

        assertEquals(usuario.getId(), usuarioDTO.getId());
        assertEquals(usuario.getUsername(), usuarioDTO.getUsername());
        assertEquals(usuario.getSenha(), usuarioDTO.getSenha());
        assertEquals(usuario.getDataInclusao(), usuarioDTO.getDataInclusao());
    }

    @Test
    void testBuilder() {
        Long id = 1L;
        String username = "testUser";
        String senha = "testPassword";
        Date dataInclusao = new Date();

        UsuarioDTO usuarioDTO = UsuarioDTO.builder()
            .id(id)
            .username(username)
            .senha(senha)
            .dataInclusao(dataInclusao)
            .build();

        assertEquals(id, usuarioDTO.getId());
        assertEquals(username, usuarioDTO.getUsername());
        assertEquals(senha, usuarioDTO.getSenha());
        assertEquals(dataInclusao, usuarioDTO.getDataInclusao());
    }

    @Test
    void testNoArgsConstructor() {
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        assertNotNull(usuarioDTO);
    }

    @Test
    void testAllArgsConstructor() {
        Long id = 1L;
        String username = "testUser";
        String senha = "testPassword";
        Date dataInclusao = new Date();

        UsuarioDTO usuarioDTO = new UsuarioDTO(id, username, senha, dataInclusao);

        assertEquals(id, usuarioDTO.getId());
        assertEquals(username, usuarioDTO.getUsername());
        assertEquals(senha, usuarioDTO.getSenha());
        assertEquals(dataInclusao, usuarioDTO.getDataInclusao());
    }

    @Test
    void testOverloadedConstructor() {
        String username = "testUser";
        String senha = "testPassword";

        UsuarioDTO usuarioDTO = new UsuarioDTO(username, senha);

        assertNull(usuarioDTO.getId());
        assertEquals(username, usuarioDTO.getUsername());
        assertEquals(senha, usuarioDTO.getSenha());
        assertNull(usuarioDTO.getDataInclusao());
    }
}

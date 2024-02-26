package br.com.lcano.centraldecontrole.service;

import br.com.lcano.centraldecontrole.dto.UsuarioDTO;
import br.com.lcano.centraldecontrole.domain.Usuario;
import br.com.lcano.centraldecontrole.repository.UsuarioRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@SpringBootTest
public class UsuarioServiceTest {
    @Mock
    private UsuarioRepository usuarioRepository;
    @InjectMocks
    private UsuarioService usuarioService;

    @Test
    void testGetTodosUsuarios() {
        Usuario usuario1 = new Usuario("Usuario1", "senha1", new Date());
        Usuario usuario2 = new Usuario("Usuario2", "senha2", new Date());
        usuario1.setId(1L);
        usuario2.setId(2L);

        List<Usuario> listaUsuario = Arrays.asList(usuario1, usuario2);

        when(usuarioRepository.findAll()).thenReturn(listaUsuario);

        List<UsuarioDTO> resultado = usuarioService.getTodosUsuarios();

        assertNotNull(resultado);
        assertEquals(2, resultado.size());

        UsuarioDTO usuarioDTO1 = resultado.get(0);
        assertEquals(usuario1.getId(), usuarioDTO1.getId());
        assertEquals(usuario1.getUsername(), usuarioDTO1.getUsername());
        assertEquals(usuario1.getSenha(), usuarioDTO1.getSenha());
        assertEquals(usuario1.getDataInclusao(), usuarioDTO1.getDataInclusao());

        UsuarioDTO usuarioDTO2 = resultado.get(1);
        assertEquals(usuario2.getId(), usuarioDTO2.getId());
        assertEquals(usuario2.getUsername(), usuarioDTO2.getUsername());
        assertEquals(usuario2.getSenha(), usuarioDTO2.getSenha());
        assertEquals(usuario2.getDataInclusao(), usuarioDTO2.getDataInclusao());
    }
}

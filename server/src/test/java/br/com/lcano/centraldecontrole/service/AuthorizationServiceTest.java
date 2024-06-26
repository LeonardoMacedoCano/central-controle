package br.com.lcano.centraldecontrole.service;

import br.com.lcano.centraldecontrole.dto.UsuarioDTO;
import br.com.lcano.centraldecontrole.domain.Usuario;
import br.com.lcano.centraldecontrole.exception.UsuarioException;
import br.com.lcano.centraldecontrole.repository.UsuarioRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.mockito.Mockito;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;

@SpringBootTest
public class AuthorizationServiceTest {
    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private AuthorizationService authorizationService;

    @Test
    void testLoadUserByUsername() {
        String username = "teste usuário";
        Usuario usuario = new Usuario(username, "senha123", new Date());
        when(usuarioRepository.findByUsername(username)).thenReturn(usuario);

        UserDetails userDetails = authorizationService.loadUserByUsername(username);

        assertNotNull(userDetails);
        assertEquals(username, userDetails.getUsername());
    }

    @Test
    public void testCadastrarUsuario_UsuarioJaCadastrado() {
        when(usuarioRepository.findByUsername(anyString())).thenReturn(new Usuario());

        UsuarioDTO usuarioDTO = new UsuarioDTO("usuario ja cadastrado", "senha123");

        assertThrows(UsuarioException.UsuarioJaCadastrado.class, () -> authorizationService.cadastrarUsuario(usuarioDTO));

        Mockito.verify(usuarioRepository, Mockito.never()).save(Mockito.any(Usuario.class));
    }

    @Test
    public void testUsuarioJaCadastrado() {
        when(usuarioRepository.findByUsername("existingUsername")).thenReturn(mock(UserDetails.class));
        when(usuarioRepository.findByUsername("nonExistingUsername")).thenReturn(null);

        assertTrue(authorizationService.usuarioJaCadastrado("existingUsername"));
        assertFalse(authorizationService.usuarioJaCadastrado("nonExistingUsername"));
    }

    @Test
    public void testUsuarioAtivo() {
        UserDetails userDetails = mock(UserDetails.class);
        when(userDetails.isEnabled()).thenReturn(true);
        when(usuarioRepository.findByUsername("activeUser")).thenReturn(userDetails);

        assertTrue(authorizationService.usuarioAtivo("activeUser"));

        when(userDetails.isEnabled()).thenReturn(false);
        assertFalse(authorizationService.usuarioAtivo("activeUser"));
    }
}

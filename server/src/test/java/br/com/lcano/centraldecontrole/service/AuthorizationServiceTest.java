package br.com.lcano.centraldecontrole.service;

import br.com.lcano.centraldecontrole.dto.UsuarioDTO;
import br.com.lcano.centraldecontrole.domain.Usuario;
import br.com.lcano.centraldecontrole.exception.UsuarioException;
import br.com.lcano.centraldecontrole.repository.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;
import org.mockito.Mockito;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;

public class AuthorizationServiceTest {
    @Mock
    private UsuarioRepository usuarioRepository;
    @Mock
    private UsuarioService usuarioService;
    @Mock
    private TokenService tokenService;

    @InjectMocks
    private AuthorizationService authorizationService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        authorizationService = new AuthorizationService(usuarioRepository, usuarioService, tokenService);
    }

    @Test
    void testLoadUserByUsername() {
        String username = "teste usuário";
        Usuario usuario = new Usuario(1L, username, "senha123", new Date(), true);

        when(usuarioService.findByUsername(username)).thenReturn(usuario);

        UserDetails userDetails = authorizationService.loadUserByUsername(username);

        assertNotNull(userDetails);
        assertEquals(username, userDetails.getUsername());
    }

    @Test
    public void testCadastrarUsuario_UsuarioJaCadastrado() {
        when(usuarioService.findByUsername(anyString())).thenReturn(new Usuario());

        UsuarioDTO usuarioDTO = new UsuarioDTO("usuario ja cadastrado", "senha123");

        assertThrows(UsuarioException.UsuarioJaCadastrado.class, () -> authorizationService.register(usuarioDTO));

        Mockito.verify(usuarioRepository, Mockito.never()).save(Mockito.any(Usuario.class));
    }

    @Test
    public void testUsuarioJaCadastrado() {
        when(authorizationService.loadUserByUsername("existingUsername")).thenReturn(mock(UserDetails.class));
        when(authorizationService.loadUserByUsername("nonExistingUsername")).thenReturn(null);

        assertTrue(authorizationService.usuarioJaCadastrado("existingUsername"));
        assertFalse(authorizationService.usuarioJaCadastrado("nonExistingUsername"));
    }

    @Test
    public void testUsuarioAtivo() {
        UserDetails userDetails = mock(UserDetails.class);
        when(userDetails.isEnabled()).thenReturn(true);
        when(authorizationService.loadUserByUsername("activeUser")).thenReturn(userDetails);

        assertTrue(authorizationService.usuarioAtivo("activeUser"));

        when(userDetails.isEnabled()).thenReturn(false);
        assertFalse(authorizationService.usuarioAtivo("activeUser"));
    }
}

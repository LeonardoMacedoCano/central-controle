package br.com.lcano.centraldecontrole.resource;

import br.com.lcano.centraldecontrole.dto.LoginDTO;
import br.com.lcano.centraldecontrole.dto.UsuarioDTO;
import br.com.lcano.centraldecontrole.exception.UsuarioException;
import br.com.lcano.centraldecontrole.repository.UsuarioRepository;
import br.com.lcano.centraldecontrole.service.AuthorizationService;
import br.com.lcano.centraldecontrole.service.TokenService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class AuthenticationResourceTest {
    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private AuthorizationService authorizationService;

    @Mock
    private TokenService tokenService;

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private AuthenticationResource authenticationResource;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        authenticationResource = new AuthenticationResource(authenticationManager, authorizationService, tokenService, usuarioRepository);
    }

    @Test
    void testLogin_UsuarioNaoEncontrado() {
        UsuarioDTO usuarioDTO = new UsuarioDTO("username", "senha");
        when(authorizationService.usuarioJaCadastrado(usuarioDTO.getUsername())).thenReturn(false);
        assertThrows(UsuarioException.UsuarioNaoEncontrado.class, () -> authenticationResource.login(usuarioDTO));
    }

    @Test
    void testLogin_UsuarioDesativado() {
        UsuarioDTO usuarioDTO = new UsuarioDTO("username", "senha");
        when(authorizationService.usuarioJaCadastrado(usuarioDTO.getUsername())).thenReturn(true);
        when(authorizationService.usuarioAtivo(usuarioDTO.getUsername())).thenReturn(false);
        assertThrows(UsuarioException.UsuarioDesativado.class, () -> authenticationResource.login(usuarioDTO));
    }

    @Test
    void testLogin_Sucesso() {
        UsuarioDTO usuarioDTO = new UsuarioDTO("username", "senha");
        when(authorizationService.usuarioJaCadastrado(usuarioDTO.getUsername())).thenReturn(true);
        when(authorizationService.usuarioAtivo(usuarioDTO.getUsername())).thenReturn(true);
        when(authenticationManager.authenticate(any())).thenReturn(mock(UsernamePasswordAuthenticationToken.class));
        when(authenticationManager.authenticate(any())).thenReturn(mock(UsernamePasswordAuthenticationToken.class));
        when(tokenService.gerarToken(any())).thenReturn("token");

        ResponseEntity<LoginDTO> response = authenticationResource.login(usuarioDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    void testRegister_Sucesso() {
        UsuarioDTO usuarioDTO = new UsuarioDTO("username", "senha");

        ResponseEntity response = authenticationResource.cadastrarUsuario(usuarioDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(authorizationService, times(1)).cadastrarUsuario(usuarioDTO);
    }

    @Test
    void testValidateToken_Sucesso() {
        String token = "token";
        String username = "username";
        when(tokenService.validateToken(token)).thenReturn(username);
        UserDetails userDetails = mock(UserDetails.class);
        when(userDetails.getUsername()).thenReturn(username);
        when(usuarioRepository.findByUsername(username)).thenReturn(userDetails);

        ResponseEntity<LoginDTO> response = authenticationResource.validateToken(token);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }
}

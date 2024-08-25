package br.com.lcano.centraldecontrole.resource;

import br.com.lcano.centraldecontrole.dto.LoginDTO;
import br.com.lcano.centraldecontrole.dto.UsuarioDTO;
import br.com.lcano.centraldecontrole.exception.UsuarioException;
import br.com.lcano.centraldecontrole.service.AuthorizationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AuthenticationResourceTest {

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private AuthorizationService authorizationService;

    @InjectMocks
    private AuthenticationResource authenticationResource;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        authenticationResource = new AuthenticationResource(authorizationService, authenticationManager);
    }

    @Test
    void testLogin_UsuarioNaoEncontrado() {
        UsuarioDTO usuarioDTO = new UsuarioDTO("username", "senha");

        when(authorizationService.login(usuarioDTO, authenticationManager))
                .thenThrow(new UsuarioException.UsuarioNaoEncontrado());

        assertThrows(UsuarioException.UsuarioNaoEncontrado.class, () -> authenticationResource.login(usuarioDTO));
    }

    @Test
    void testLogin_UsuarioDesativado() {
        UsuarioDTO usuarioDTO = new UsuarioDTO("username", "senha");

        when(authorizationService.login(usuarioDTO, authenticationManager))
                .thenThrow(new UsuarioException.UsuarioDesativado());

        assertThrows(UsuarioException.UsuarioDesativado.class, () -> authenticationResource.login(usuarioDTO));
    }

    @Test
    void testLogin_Sucesso() {
        UsuarioDTO usuarioDTO = new UsuarioDTO("username", "senha");
        LoginDTO loginDTO = new LoginDTO("username", "token");

        when(authorizationService.login(usuarioDTO, authenticationManager)).thenReturn(loginDTO);

        ResponseEntity<LoginDTO> response = authenticationResource.login(usuarioDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(loginDTO, response.getBody());
    }

    @Test
    void testRegister_Sucesso() {
        UsuarioDTO usuarioDTO = new UsuarioDTO("username", "senha");

        ResponseEntity<Void> response = authenticationResource.register(usuarioDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(authorizationService, times(1)).register(usuarioDTO);
    }

    @Test
    void testValidateToken_Sucesso() {
        String token = "token";
        LoginDTO loginDTO = new LoginDTO("username", token);

        when(authorizationService.validateToken(token)).thenReturn(loginDTO);

        ResponseEntity<LoginDTO> response = authenticationResource.validateToken(token);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(loginDTO, response.getBody());
    }
}
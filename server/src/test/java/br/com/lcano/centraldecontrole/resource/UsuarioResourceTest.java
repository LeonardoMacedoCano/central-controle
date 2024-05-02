package br.com.lcano.centraldecontrole.resource;

import br.com.lcano.centraldecontrole.dto.UsuarioDTO;
import br.com.lcano.centraldecontrole.service.UsuarioService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class UsuarioResourceTest {
    @Mock
    private UsuarioService usuarioService;

    @InjectMocks
    private UsuarioResource usuarioResource;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetTodosUsuarios() {
        List<UsuarioDTO> usuarios = new ArrayList<>();
        usuarios.add(new UsuarioDTO("1", "Joao"));
        usuarios.add(new UsuarioDTO("2", "Maria"));

        when(usuarioService.getTodosUsuarios()).thenReturn(usuarios);

        ResponseEntity<List<UsuarioDTO>> responseEntity = usuarioResource.getTodosUsuarios();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(usuarios.size(), Objects.requireNonNull(responseEntity.getBody()).size());
    }
}

package br.com.lcano.centraldecontrole.service;

import br.com.lcano.centraldecontrole.domain.Usuario;
import br.com.lcano.centraldecontrole.repository.UsuarioRepository;
import br.com.lcano.centraldecontrole.util.DateUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;

public class UsuarioServiceTest {
    @Mock
    private UsuarioRepository usuarioRepository;
    @Mock
    private UsuarioConfigService usuarioConfigService;
    @Mock
    private DateUtil dateUtil;
    @InjectMocks
    private UsuarioService usuarioService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        usuarioService = new UsuarioService(usuarioRepository, usuarioConfigService, dateUtil);
    }

    @Test
    void testGerarUsuario() {
        String username = "testuser";
        String senha = "password";

        usuarioService.register(username, senha);

        verify(usuarioRepository, times(1)).save(any(Usuario.class));
        verify(usuarioConfigService, times(1)).createAndSaveUsuarioConfig(any(Usuario.class));
    }
}

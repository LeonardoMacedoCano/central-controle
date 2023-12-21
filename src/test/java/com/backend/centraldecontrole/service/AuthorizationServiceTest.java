package com.backend.centraldecontrole.service;

import com.backend.centraldecontrole.model.Usuario;
import com.backend.centraldecontrole.repository.UsuarioRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import com.backend.centraldecontrole.dto.UsuarioRequestDTO;
import org.mockito.Mockito;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
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
    public void testCadastrarUsuario_UsuarioNaoCadastrado() {
        when(usuarioRepository.findByUsername(anyString())).thenReturn(null);

        UsuarioRequestDTO usuarioRequestDTO = new UsuarioRequestDTO("novo usuario", "senha123");
        authorizationService.cadastrarUsuario(usuarioRequestDTO);

        Mockito.verify(usuarioRepository).save(Mockito.any(Usuario.class));
    }


    @Test
    public void testCadastrarUsuario_UsuarioJaCadastrado() {
        when(usuarioRepository.findByUsername(anyString())).thenReturn(new Usuario());

        UsuarioRequestDTO usuarioRequestDTO = new UsuarioRequestDTO("usuario ja cadastrado", "senha123");

        assertThrows(UsernameNotFoundException.class, () -> authorizationService.cadastrarUsuario(usuarioRequestDTO));
    }
}

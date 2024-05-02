package br.com.lcano.centraldecontrole.domain;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import java.util.Collection;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class UsuarioTests {

    @Test
    void testConstrutor() {
        Date dataInclusao = new Date();
        Usuario usuario = new Usuario("testuser", "password", dataInclusao);
        assertEquals("testuser", usuario.getUsername());
        assertEquals("password", usuario.getPassword());
        assertEquals(dataInclusao, usuario.getDataInclusao());
        assertTrue(usuario.isEnabled());
    }

    @Test
    void testGetAuthorities() {
        Usuario usuario = new Usuario("testuser", "password", new Date());
        Collection<? extends GrantedAuthority> authorities = usuario.getAuthorities();
        assertEquals(1, authorities.size());
        assertTrue(authorities.contains(new SimpleGrantedAuthority("ROLE_USER")));
    }

    @Test
    void testIsAccountNonExpired() {
        Usuario usuario = new Usuario("testuser", "password", new Date());
        assertTrue(usuario.isAccountNonExpired());
    }

    @Test
    void testIsAccountNonLocked() {
        Usuario usuario = new Usuario("testuser", "password", new Date());
        assertTrue(usuario.isAccountNonLocked());
    }

    @Test
    void testIsCredentialsNonExpired() {
        Usuario usuario = new Usuario("testuser", "password", new Date());
        assertTrue(usuario.isCredentialsNonExpired());
    }

    @Test
    void testIsEnabled() {
        Usuario usuarioAtivo = new Usuario("testuser", "password", new Date());
        assertTrue(usuarioAtivo.isEnabled());

        Usuario usuarioInativo = new Usuario("testuser", "password", new Date());
        usuarioInativo.setAtivo(false);
        assertFalse(usuarioInativo.isEnabled());
    }
}


package br.com.lcano.centraldecontrole.domain;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
public class UsuarioConfigTest {

    @Test
    void testConstructor() {
        Date dataInclusao = new Date();
        Usuario usuario = new Usuario("testuser", "password", dataInclusao);
        UsuarioConfig usuarioConfig = new UsuarioConfig(usuario);

        assertEquals(usuario, usuarioConfig.getUsuario());
        assertEquals(10, usuarioConfig.getDespesaNumeroItemPagina());
        assertEquals(0.0, usuarioConfig.getDespesaValorMetaMensal());
        assertEquals(10, usuarioConfig.getDespesaDiaPadraoVencimento());
    }

    @Test
    void testGettersAndSetters() {
        UsuarioConfig usuarioConfig = new UsuarioConfig();
        Usuario usuario = mock(Usuario.class);
        FormaPagamento formaPagamento = mock(FormaPagamento.class);

        usuarioConfig.setId(1L);
        usuarioConfig.setUsuario(usuario);
        usuarioConfig.setDespesaNumeroItemPagina(20);
        usuarioConfig.setDespesaValorMetaMensal(100.0);
        usuarioConfig.setDespesaDiaPadraoVencimento(15);
        usuarioConfig.setDespesaFormaPagamentoPadrao(formaPagamento);

        assertEquals(1L, usuarioConfig.getId());
        assertEquals(usuario, usuarioConfig.getUsuario());
        assertEquals(20, usuarioConfig.getDespesaNumeroItemPagina());
        assertEquals(100.0, usuarioConfig.getDespesaValorMetaMensal());
        assertEquals(15, usuarioConfig.getDespesaDiaPadraoVencimento());
        assertEquals(formaPagamento, usuarioConfig.getDespesaFormaPagamentoPadrao());
    }

    @Test
    void testEqualsAndHashCode() {
        UsuarioConfig usuarioConfig1 = new UsuarioConfig();
        UsuarioConfig usuarioConfig2 = new UsuarioConfig();

        assertEquals(usuarioConfig1, usuarioConfig2);
        assertEquals(usuarioConfig1.hashCode(), usuarioConfig2.hashCode());

        usuarioConfig1.setId(1L);
        assertNotEquals(usuarioConfig1, usuarioConfig2);
        assertNotEquals(usuarioConfig1.hashCode(), usuarioConfig2.hashCode());

        usuarioConfig2.setId(1L);
        assertEquals(usuarioConfig1, usuarioConfig2);
        assertEquals(usuarioConfig1.hashCode(), usuarioConfig2.hashCode());
    }

    @Test
    void testToString() {
        UsuarioConfig usuarioConfig = new UsuarioConfig();
        usuarioConfig.setId(1L);
        assertEquals("UsuarioConfig(id=1)", usuarioConfig.toString());
    }
}

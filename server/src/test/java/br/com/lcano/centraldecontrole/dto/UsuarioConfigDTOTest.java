package br.com.lcano.centraldecontrole.dto;

import br.com.lcano.centraldecontrole.domain.Usuario;
import br.com.lcano.centraldecontrole.domain.UsuarioConfig;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UsuarioConfigDTOTest {

    @Test
    void testConverterParaDTO() {
        Usuario usuario = new Usuario();
        usuario.setId(1L);
        usuario.setUsername("Teste");

        DespesaFormaPagamento formaPagamento = new DespesaFormaPagamento();
        formaPagamento.setId(1L);
        formaPagamento.setDescricao("Cartao");

        UsuarioConfig usuarioConfig = new UsuarioConfig();
        usuarioConfig.setId(1L);
        usuarioConfig.setUsuario(usuario);
        usuarioConfig.setDespesaNumeroMaxItemPagina(5);
        usuarioConfig.setDespesaDiaPadraoVencimento(15);
        usuarioConfig.setDespesaValorMetaMensal(1500.00);
        usuarioConfig.setDespesaFormaPagamentoPadrao(formaPagamento);

        UsuarioConfigDTO dto = UsuarioConfigDTO.converterParaDTO(usuarioConfig);

        assertEquals(usuarioConfig.getId(), dto.getId());
        assertEquals(usuarioConfig.getDespesaNumeroMaxItemPagina(), dto.getDespesaNumeroMaxItemPagina());
        assertEquals(usuarioConfig.getDespesaDiaPadraoVencimento(), dto.getDespesaDiaPadraoVencimento());
        assertEquals(usuarioConfig.getDespesaValorMetaMensal(), dto.getDespesaValorMetaMensal());
        assertEquals(usuarioConfig.getDespesaFormaPagamentoPadrao(), dto.getDespesaFormaPagamentoPadrao());
    }
}

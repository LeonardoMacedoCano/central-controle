package br.com.lcano.centraldecontrole.dto.fluxocaixa;

import br.com.lcano.centraldecontrole.domain.fluxocaixa.DespesaFormaPagamento;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DespesaFormaPagamentoDTOTest {

    @Test
    void testConverterParaDTO() {
        DespesaFormaPagamento formaPagamento = new DespesaFormaPagamento();
        formaPagamento.setId(1L);
        formaPagamento.setDescricao("Pix");

        DespesaFormaPagamentoDTO dto = DespesaFormaPagamentoDTO.converterParaDTO(formaPagamento);

        assertEquals(formaPagamento.getId(), dto.getId());
        assertEquals(formaPagamento.getDescricao(), dto.getDescricao());
    }
}

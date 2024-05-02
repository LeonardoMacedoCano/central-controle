package br.com.lcano.centraldecontrole.dto;

import br.com.lcano.centraldecontrole.domain.FormaPagamento;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FormaPagamentoDTOTest {

    @Test
    void testConverterParaDTO() {
        FormaPagamento formaPagamento = new FormaPagamento();
        formaPagamento.setId(1L);
        formaPagamento.setDescricao("Pix");

        FormaPagamentoDTO dto = FormaPagamentoDTO.converterParaDTO(formaPagamento);

        assertEquals(formaPagamento.getId(), dto.getId());
        assertEquals(formaPagamento.getDescricao(), dto.getDescricao());
    }
}

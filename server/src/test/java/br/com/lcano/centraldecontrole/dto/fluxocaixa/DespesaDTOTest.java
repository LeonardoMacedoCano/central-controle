package br.com.lcano.centraldecontrole.dto.fluxocaixa;

import br.com.lcano.centraldecontrole.domain.fluxocaixa.DespesaCategoria;
import br.com.lcano.centraldecontrole.domain.fluxocaixa.Despesa;
import br.com.lcano.centraldecontrole.domain.fluxocaixa.DespesaParcela;
import br.com.lcano.centraldecontrole.domain.fluxocaixa.DespesaFormaPagamento;
import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

public class DespesaDTOTest {

    @Test
    void testConverterParaDTO() {
        DespesaCategoria categoria = new DespesaCategoria();
        categoria.setId(1L);
        categoria.setDescricao("Categoria de Teste");

        DespesaFormaPagamento formaPagamento = new DespesaFormaPagamento();
        formaPagamento.setId(1L);
        formaPagamento.setDescricao("Cartao");

        Despesa despesa = new Despesa();
        despesa.setId(1L);
        despesa.setCategoria(categoria);
        despesa.setParcelas(Arrays.asList(
                new DespesaParcela(1L, 1, new Date(), 5.00, false, despesa, formaPagamento),
                new DespesaParcela(2L, 2, new Date(), 10.00, false, despesa, formaPagamento),
                new DespesaParcela(3L, 3, new Date(), 15.00, false, despesa, formaPagamento)));

        Double valorTotalEsperado = 30.00;
        DespesaDTO dto = DespesaDTO.converterParaDTO(despesa);

        assertEquals(despesa.getId(), dto.getId());
        assertNotNull(dto.getCategoria());
        assertEquals(despesa.getCategoria().getId(), dto.getCategoria().getId());
        assertEquals(despesa.getCategoria().getDescricao(), dto.getCategoria().getDescricao());
        assertEquals(valorTotalEsperado, dto.getValorTotal());
        assertEquals("Não pago", dto.getSituacao());
    }
}

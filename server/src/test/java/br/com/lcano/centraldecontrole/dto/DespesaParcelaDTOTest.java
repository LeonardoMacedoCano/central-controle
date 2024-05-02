package br.com.lcano.centraldecontrole.dto;

import br.com.lcano.centraldecontrole.domain.DespesaParcela;
import br.com.lcano.centraldecontrole.domain.FormaPagamento;
import org.junit.jupiter.api.Test;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class DespesaParcelaDTOTest {

    @Test
    void testConverterParaDTO() {
        DespesaParcela despesaParcela = new DespesaParcela();
        despesaParcela.setId(1L);
        despesaParcela.setNumero(1);
        despesaParcela.setDataVencimento(new Date());
        despesaParcela.setValor(100.0);
        despesaParcela.setPago(true);

        FormaPagamento formaPagamento = new FormaPagamento(1L, "Cartao");
        despesaParcela.setFormaPagamento(formaPagamento);

        DespesaParcelaDTO dto = DespesaParcelaDTO.converterParaDTO(despesaParcela);

        assertEquals(despesaParcela.getId(), dto.getId());
        assertEquals(despesaParcela.getNumero(), dto.getNumero());
        assertEquals(despesaParcela.getDataVencimento(), dto.getDataVencimento());
        assertEquals(despesaParcela.getValor(), dto.getValor());
        assertEquals(despesaParcela.isPago(), dto.getPago());
        assertEquals(despesaParcela.getFormaPagamento().getId(), dto.getFormaPagamento().getId());
        assertEquals(despesaParcela.getFormaPagamento().getDescricao(), dto.getFormaPagamento().getDescricao());
    }

    @Test
    void testConverterListaParaDTO() {
        FormaPagamento formaPagamentoCartao = new FormaPagamento(1L, "Cartao");
        FormaPagamento formaPagamentoPix = new FormaPagamento(2L, "Pix");

        DespesaParcela despesaParcela1 = new DespesaParcela();
        despesaParcela1.setId(1L);
        despesaParcela1.setNumero(1);
        despesaParcela1.setDataVencimento(new Date());
        despesaParcela1.setValor(100.0);
        despesaParcela1.setPago(true);
        despesaParcela1.setFormaPagamento(formaPagamentoCartao);

        DespesaParcela despesaParcela2 = new DespesaParcela();
        despesaParcela2.setId(2L);
        despesaParcela2.setNumero(2);
        despesaParcela2.setDataVencimento(new Date());
        despesaParcela2.setValor(200.0);
        despesaParcela2.setPago(false);
        despesaParcela2.setFormaPagamento(formaPagamentoPix);

        DespesaParcela despesaParcela3 = new DespesaParcela();
        despesaParcela3.setId(3L);
        despesaParcela3.setNumero(3);
        despesaParcela3.setDataVencimento(new Date());
        despesaParcela3.setValor(200.0);
        despesaParcela3.setPago(false);

        List<DespesaParcela> despesaParcelas = List.of(despesaParcela1, despesaParcela2, despesaParcela3);

        List<DespesaParcelaDTO> dtos = DespesaParcelaDTO.converterListaParaDTO(despesaParcelas);

        assertEquals(3, dtos.size());

        assertEquals(despesaParcela1.getId(), dtos.get(0).getId());
        assertEquals(despesaParcela2.getId(), dtos.get(1).getId());
        assertEquals(despesaParcela3.getId(), dtos.get(2).getId());

        assertEquals(despesaParcela1.getNumero(), dtos.get(0).getNumero());
        assertEquals(despesaParcela2.getNumero(), dtos.get(1).getNumero());
        assertEquals(despesaParcela3.getNumero(), dtos.get(2).getNumero());

        assertEquals(despesaParcela1.getDataVencimento(), dtos.get(0).getDataVencimento());
        assertEquals(despesaParcela2.getDataVencimento(), dtos.get(1).getDataVencimento());
        assertEquals(despesaParcela3.getDataVencimento(), dtos.get(2).getDataVencimento());

        assertEquals(despesaParcela1.getValor(), dtos.get(0).getValor());
        assertEquals(despesaParcela2.getValor(), dtos.get(1).getValor());
        assertEquals(despesaParcela3.getValor(), dtos.get(2).getValor());

        assertEquals(despesaParcela1.isPago(), dtos.get(0).getPago());
        assertEquals(despesaParcela2.isPago(), dtos.get(1).getPago());
        assertEquals(despesaParcela3.isPago(), dtos.get(2).getPago());

        assertEquals(despesaParcela1.getFormaPagamento().getId(), dtos.get(0).getFormaPagamento().getId());
        assertEquals(despesaParcela2.getFormaPagamento().getId(), dtos.get(1).getFormaPagamento().getId());

        assertEquals(despesaParcela1.getFormaPagamento().getDescricao(), dtos.get(0).getFormaPagamento().getDescricao());
        assertEquals(despesaParcela2.getFormaPagamento().getDescricao(), dtos.get(1).getFormaPagamento().getDescricao());

        assertNull(dtos.get(2).getFormaPagamento());
    }
}

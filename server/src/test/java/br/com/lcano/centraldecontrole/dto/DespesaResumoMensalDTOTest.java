package br.com.lcano.centraldecontrole.dto;

import br.com.lcano.centraldecontrole.domain.CategoriaDespesa;
import br.com.lcano.centraldecontrole.domain.Despesa;
import br.com.lcano.centraldecontrole.domain.DespesaParcela;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class DespesaResumoMensalDTOTest {
    @Test
    void testConverterParaDTO() {
        DespesaParcela parcela1 = new DespesaParcela();
        parcela1.setValor(100.0);
        parcela1.setPago(true);

        DespesaParcela parcela2 = new DespesaParcela();
        parcela2.setValor(200.0);
        parcela2.setPago(false);

        List<DespesaParcela> parcelas = List.of(parcela1, parcela2);

        Despesa despesa = new Despesa();
        despesa.setId(1L);
        despesa.setCategoria(new CategoriaDespesa());
        despesa.setDescricao("Despesa de teste");
        despesa.setParcelas(parcelas);

        DespesaResumoMensalDTO dto = DespesaResumoMensalDTO.converterParaDTO(despesa);

        assertEquals(despesa.getId(), dto.getId());
        assertNotNull(dto.getCategoria());
        assertEquals(despesa.getDescricao(), dto.getDescricao());
        assertEquals(300.0, dto.getValorTotal());
        assertEquals("Parcialmente pago", dto.getSituacao());
    }

    @Test
    void testCalcularValorTotal() {
        List<DespesaParcelaDTO> parcelas = new ArrayList<>();
        DespesaParcelaDTO parcela1 = new DespesaParcelaDTO();
        parcela1.setValor(100.0);
        DespesaParcelaDTO parcela2 = new DespesaParcelaDTO();
        parcela2.setValor(200.0);
        parcelas.add(parcela1);
        parcelas.add(parcela2);

        assertEquals(300.0, DespesaResumoMensalDTO.calcularValorTotal(parcelas));
    }

    @Test
    void testCalcularSituacao() {
        List<DespesaParcelaDTO> todasPagas = new ArrayList<>();
        DespesaParcelaDTO parcela1 = new DespesaParcelaDTO();
        parcela1.setPago(true);
        DespesaParcelaDTO parcela2 = new DespesaParcelaDTO();
        parcela2.setPago(true);
        todasPagas.add(parcela1);
        todasPagas.add(parcela2);

        assertEquals("Pago", DespesaResumoMensalDTO.calcularSituacao(todasPagas));

        List<DespesaParcelaDTO> todasNaoPagas = new ArrayList<>();
        DespesaParcelaDTO parcelaNaoPaga1 = new DespesaParcelaDTO();
        parcelaNaoPaga1.setPago(false);
        DespesaParcelaDTO parcelaNaoPaga2 = new DespesaParcelaDTO();
        parcelaNaoPaga2.setPago(false);
        todasNaoPagas.add(parcelaNaoPaga1);
        todasNaoPagas.add(parcelaNaoPaga2);

        assertEquals("Não pago", DespesaResumoMensalDTO.calcularSituacao(todasNaoPagas));

        List<DespesaParcelaDTO> parcialmentePagas = new ArrayList<>();
        DespesaParcelaDTO parcelaPaga = new DespesaParcelaDTO();
        parcelaPaga.setPago(true);
        DespesaParcelaDTO parcelaNaoPaga = new DespesaParcelaDTO();
        parcelaNaoPaga.setPago(false);
        parcialmentePagas.add(parcelaPaga);
        parcialmentePagas.add(parcelaNaoPaga);

        assertEquals("Parcialmente pago", DespesaResumoMensalDTO.calcularSituacao(parcialmentePagas));
    }
}

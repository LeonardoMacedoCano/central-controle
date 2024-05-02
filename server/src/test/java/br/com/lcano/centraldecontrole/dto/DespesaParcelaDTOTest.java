package br.com.lcano.centraldecontrole.dto;

import br.com.lcano.centraldecontrole.domain.DespesaParcela;
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

        DespesaParcelaDTO dto = DespesaParcelaDTO.converterParaDTO(despesaParcela);

        assertEquals(despesaParcela.getId(), dto.getId());
        assertEquals(despesaParcela.getNumero(), dto.getNumero());
        assertEquals(despesaParcela.getDataVencimento(), dto.getDataVencimento());
        assertEquals(despesaParcela.getValor(), dto.getValor());
        assertEquals(despesaParcela.isPago(), dto.getPago());
    }

    @Test
    void testConverterListaParaDTO() {
        DespesaParcela despesaParcela1 = new DespesaParcela();
        despesaParcela1.setId(1L);
        despesaParcela1.setNumero(1);
        despesaParcela1.setDataVencimento(new Date());
        despesaParcela1.setValor(100.0);
        despesaParcela1.setPago(true);

        DespesaParcela despesaParcela2 = new DespesaParcela();
        despesaParcela2.setId(2L);
        despesaParcela2.setNumero(2);
        despesaParcela2.setDataVencimento(new Date());
        despesaParcela2.setValor(200.0);
        despesaParcela2.setPago(false);

        List<DespesaParcela> despesaParcelas = List.of(despesaParcela1, despesaParcela2);

        List<DespesaParcelaDTO> dtos = DespesaParcelaDTO.converterListaParaDTO(despesaParcelas);

        assertEquals(2, dtos.size());
        assertEquals(despesaParcela1.getId(), dtos.get(0).getId());
        assertEquals(despesaParcela2.getId(), dtos.get(1).getId());
        assertEquals(despesaParcela1.getNumero(), dtos.get(0).getNumero());
        assertEquals(despesaParcela2.getNumero(), dtos.get(1).getNumero());
        assertEquals(despesaParcela1.getDataVencimento(), dtos.get(0).getDataVencimento());
        assertEquals(despesaParcela2.getDataVencimento(), dtos.get(1).getDataVencimento());
        assertEquals(despesaParcela1.getValor(), dtos.get(0).getValor());
        assertEquals(despesaParcela2.getValor(), dtos.get(1).getValor());
        assertEquals(despesaParcela1.isPago(), dtos.get(0).getPago());
        assertEquals(despesaParcela2.isPago(), dtos.get(1).getPago());
    }
}

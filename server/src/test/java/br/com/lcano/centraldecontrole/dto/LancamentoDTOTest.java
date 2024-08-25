package br.com.lcano.centraldecontrole.dto;

import br.com.lcano.centraldecontrole.domain.Lancamento;
import br.com.lcano.centraldecontrole.domain.Usuario;
import br.com.lcano.centraldecontrole.dto.fluxocaixa.DespesaDTO;
import br.com.lcano.centraldecontrole.enums.TipoLancamentoEnum;
import org.junit.jupiter.api.Test;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LancamentoDTOTest {

    @Test
    void testConverterParaDTO() {
        Lancamento lancamento = new Lancamento(1L, new Usuario(), new Date(), "teste", TipoLancamentoEnum.DESPESA);
        DespesaDTO despesaDTO = new DespesaDTO();

        LancamentoDTO dto = LancamentoDTO.converterParaDTO(lancamento, despesaDTO);

        assertEquals(lancamento.getId(), dto.getId());
        assertEquals(lancamento.getDataLancamento(), dto.getDataLancamento());
        assertEquals(lancamento.getDescricao(), dto.getDescricao());
        assertEquals(lancamento.getTipo(), dto.getTipo());
        assertEquals(despesaDTO, dto.getItemDTO());
    }
}
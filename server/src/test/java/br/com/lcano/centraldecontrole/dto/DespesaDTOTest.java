package br.com.lcano.centraldecontrole.dto;

import br.com.lcano.centraldecontrole.domain.CategoriaDespesa;
import br.com.lcano.centraldecontrole.domain.Despesa;
import org.junit.jupiter.api.Test;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

public class DespesaDTOTest {

    @Test
    void testConverterParaDTO() {
        CategoriaDespesa categoria = new CategoriaDespesa();
        categoria.setId(1L);
        categoria.setDescricao("Categoria de Teste");

        Despesa despesa = new Despesa();
        despesa.setId(1L);
        despesa.setDescricao("Despesa de Teste");
        despesa.setDataLancamento(new Date());
        despesa.setCategoria(categoria);

        DespesaDTO dto = DespesaDTO.converterParaDTO(despesa);

        assertEquals(despesa.getId(), dto.getId());
        assertEquals(despesa.getDescricao(), dto.getDescricao());
        assertEquals(despesa.getDataLancamento(), dto.getDataLancamento());
        assertNotNull(dto.getCategoria());
        assertEquals(despesa.getCategoria().getId(), dto.getCategoria().getId());
        assertEquals(despesa.getCategoria().getDescricao(), dto.getCategoria().getDescricao());
    }
}

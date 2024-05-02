package br.com.lcano.centraldecontrole.dto;

import br.com.lcano.centraldecontrole.domain.Categoria;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CategoriaDTOTest {

    @Test
    void testConverterParaDTO() {
        Categoria categoria = new Categoria();
        categoria.setId(1L);
        categoria.setDescricao("Categoria Teste");

        CategoriaDTO dto = CategoriaDTO.converterParaDTO(categoria);

        assertEquals(categoria.getId(), dto.getId());
        assertEquals(categoria.getDescricao(), dto.getDescricao());
    }
}

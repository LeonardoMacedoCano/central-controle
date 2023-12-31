package com.backend.centraldecontrole.service;

import com.backend.centraldecontrole.dto.CategoriaDespesaResponseDTO;
import com.backend.centraldecontrole.model.CategoriaDespesa;
import com.backend.centraldecontrole.repository.CategoriaDespesaRepository;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@SpringBootTest
public class CategoriaDespesaServiceTest {
    @Mock
    private CategoriaDespesaRepository categoriaDespesaRepository;
    @InjectMocks
    private CategoriaDespesaService categoriaDespesaService;

    @Test
    void testGetTodasCategoriasDespesa() {
        CategoriaDespesa categoria1 = new CategoriaDespesa(1L, "Categoria1");
        CategoriaDespesa categoria2 = new CategoriaDespesa(2L, "Categoria2");

        List<CategoriaDespesa> listaCategoriaDespesa = Arrays.asList(categoria1, categoria2);

        when(categoriaDespesaRepository.findAll()).thenReturn(listaCategoriaDespesa);

        List<CategoriaDespesaResponseDTO> resultado = categoriaDespesaService.getTodasCategoriasDespesa();

        assertNotNull(resultado);
        assertEquals(2, resultado.size());

        CategoriaDespesaResponseDTO CategoriaDespesaResponseDTO1 = resultado.get(0);
        assertEquals(categoria1.getId(), CategoriaDespesaResponseDTO1.id());
        assertEquals(categoria1.getDescricao(), CategoriaDespesaResponseDTO1.descricao());

        CategoriaDespesaResponseDTO CategoriaDespesaResponseDTO2 = resultado.get(1);
        assertEquals(categoria2.getId(), CategoriaDespesaResponseDTO2.id());
        assertEquals(categoria2.getDescricao(), CategoriaDespesaResponseDTO2.descricao());
    }
}

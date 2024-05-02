package br.com.lcano.centraldecontrole.service;

import br.com.lcano.centraldecontrole.dto.CategoriaDTO;
import br.com.lcano.centraldecontrole.domain.CategoriaTarefa;
import br.com.lcano.centraldecontrole.repository.CategoriaTarefaRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@SpringBootTest
public class CategoriaTarefaServiceTest {
    @Mock
    private CategoriaTarefaRepository categoriaTarefaRepository;
    @InjectMocks
    private CategoriaTarefaService categoriaTarefaService;

    @Test
    void testGetTodasCategoriasTarefa() {
        CategoriaTarefa categoria1 = new CategoriaTarefa("Categoria1");
        CategoriaTarefa categoria2 = new CategoriaTarefa("Categoria2");

        List<CategoriaTarefa> listaCategoriaTarefa = Arrays.asList(categoria1, categoria2);

        when(categoriaTarefaRepository.findAll()).thenReturn(listaCategoriaTarefa);

        List<CategoriaDTO> resultado = categoriaTarefaService.getTodasCategoriasTarefa();

        assertNotNull(resultado);
        assertEquals(2, resultado.size());

        CategoriaDTO CategoriaTarefaResponseDTO1 = resultado.get(0);
        assertEquals(categoria1.getDescricao(), CategoriaTarefaResponseDTO1.getDescricao());

        CategoriaDTO CategoriaTarefaResponseDTO2 = resultado.get(1);
        assertEquals(categoria2.getDescricao(), CategoriaTarefaResponseDTO2.getDescricao());
    }
}

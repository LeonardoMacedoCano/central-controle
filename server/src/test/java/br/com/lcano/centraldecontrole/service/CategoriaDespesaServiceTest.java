package br.com.lcano.centraldecontrole.service;

import br.com.lcano.centraldecontrole.dto.CategoriaDTO;
import br.com.lcano.centraldecontrole.domain.CategoriaDespesa;
import br.com.lcano.centraldecontrole.exception.DespesaException;
import br.com.lcano.centraldecontrole.repository.CategoriaDespesaRepository;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
public class CategoriaDespesaServiceTest {
    @Mock
    private CategoriaDespesaRepository categoriaDespesaRepository;
    @InjectMocks
    private CategoriaDespesaService categoriaDespesaService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        categoriaDespesaService = new CategoriaDespesaService(categoriaDespesaRepository);
    }

    @Test
    void testGetTodasCategoriasDespesa() {
        CategoriaDespesa categoria1 = new CategoriaDespesa("Categoria1");
        CategoriaDespesa categoria2 = new CategoriaDespesa("Categoria2");

        List<CategoriaDespesa> listaCategoriaDespesa = Arrays.asList(categoria1, categoria2);

        when(categoriaDespesaRepository.findAll()).thenReturn(listaCategoriaDespesa);

        List<CategoriaDTO> resultado = categoriaDespesaService.getTodasCategoriasDespesa();

        assertNotNull(resultado);
        assertEquals(2, resultado.size());

        CategoriaDTO CategoriaDespesaResponseDTO1 = resultado.get(0);
        assertEquals(categoria1.getDescricao(), CategoriaDespesaResponseDTO1.getDescricao());

        CategoriaDTO CategoriaDespesaResponseDTO2 = resultado.get(1);
        assertEquals(categoria2.getDescricao(), CategoriaDespesaResponseDTO2.getDescricao());
    }

    @Test
    void testGetCategoriaDespesaById_CategoriaExiste() {
        Long idCategoria = 1L;
        CategoriaDespesa categoriaDespesa = new CategoriaDespesa("Teste");
        categoriaDespesa.setId(idCategoria);

        when(categoriaDespesaRepository.findById(idCategoria)).thenReturn(Optional.of(categoriaDespesa));

        CategoriaDespesa result = categoriaDespesaService.getCategoriaDespesaById(idCategoria);

        assertEquals(categoriaDespesa, result);
    }

    @Test
    public void testGetCategoriaDespesaById_CategoriaNaoExiste() {
        Long idCategoria = 1L;

        when(categoriaDespesaRepository.findById(idCategoria)).thenReturn(Optional.empty());

        assertThrows(DespesaException.CategoriaDespesaNaoEncontradaById.class, () -> categoriaDespesaService.getCategoriaDespesaById(idCategoria));
    }
}

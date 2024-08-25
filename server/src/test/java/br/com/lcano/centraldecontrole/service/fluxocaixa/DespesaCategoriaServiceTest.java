package br.com.lcano.centraldecontrole.service.fluxocaixa;

import br.com.lcano.centraldecontrole.dto.CategoriaDTO;
import br.com.lcano.centraldecontrole.domain.fluxocaixa.DespesaCategoria;
import br.com.lcano.centraldecontrole.exception.fluxocaixa.DespesaException;
import br.com.lcano.centraldecontrole.repository.fluxocaixa.DespesaCategoriaRepository;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class DespesaCategoriaServiceTest {
    @Mock
    private DespesaCategoriaRepository despesaCategoriaRepository;
    @InjectMocks
    private DespesaCategoriaService despesaCategoriaService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        despesaCategoriaService = new DespesaCategoriaService(despesaCategoriaRepository);
    }

    @Test
    void testGetTodasCategoriasDespesa() {
        DespesaCategoria categoria1 = new DespesaCategoria("Categoria1");
        DespesaCategoria categoria2 = new DespesaCategoria("Categoria2");

        List<DespesaCategoria> listaCategoriaDespesa = Arrays.asList(categoria1, categoria2);

        when(despesaCategoriaRepository.findAll()).thenReturn(listaCategoriaDespesa);

        List<CategoriaDTO> resultado = despesaCategoriaService.getTodasCategoriasDespesa();

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
        DespesaCategoria categoriaDespesa = new DespesaCategoria("Teste");
        categoriaDespesa.setId(idCategoria);

        when(despesaCategoriaRepository.findById(idCategoria)).thenReturn(Optional.of(categoriaDespesa));

        DespesaCategoria result = despesaCategoriaService.getCategoriaDespesaById(idCategoria);

        assertEquals(categoriaDespesa, result);
    }

    @Test
    public void testGetCategoriaDespesaById_CategoriaNaoExiste() {
        Long idCategoria = 1L;

        when(despesaCategoriaRepository.findById(idCategoria)).thenReturn(Optional.empty());

        assertThrows(DespesaException.CategoriaNaoEncontradaById.class, () -> despesaCategoriaService.getCategoriaDespesaById(idCategoria));
    }
}

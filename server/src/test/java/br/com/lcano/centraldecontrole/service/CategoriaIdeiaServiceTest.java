package br.com.lcano.centraldecontrole.service;

import br.com.lcano.centraldecontrole.dto.CategoriaDTO;
import br.com.lcano.centraldecontrole.domain.CategoriaIdeia;
import br.com.lcano.centraldecontrole.repository.CategoriaIdeiaRepository;
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
public class CategoriaIdeiaServiceTest {
    @Mock
    private CategoriaIdeiaRepository categoriaIdeiaRepository;
    @InjectMocks
    private CategoriaIdeiaService categoriaIdeiaService;

    @Test
    void testGetTodasCategoriasIdeia() {
        CategoriaIdeia categoria1 = new CategoriaIdeia("Categoria1");
        CategoriaIdeia categoria2 = new CategoriaIdeia("Categoria2");

        List<CategoriaIdeia> listaCategoriaIdeia = Arrays.asList(categoria1, categoria2);

        when(categoriaIdeiaRepository.findAll()).thenReturn(listaCategoriaIdeia);

        List<CategoriaDTO> resultado = categoriaIdeiaService.getTodasCategoriasIdeia();

        assertNotNull(resultado);
        assertEquals(2, resultado.size());

        CategoriaDTO CategoriaIdeiaResponseDTO1 = resultado.get(0);
        assertEquals(categoria1.getDescricao(), CategoriaIdeiaResponseDTO1.getDescricao());

        CategoriaDTO CategoriaIdeiaResponseDTO2 = resultado.get(1);
        assertEquals(categoria2.getDescricao(), CategoriaIdeiaResponseDTO2.getDescricao());
    }
}

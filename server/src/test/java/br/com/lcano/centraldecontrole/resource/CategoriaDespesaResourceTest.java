package br.com.lcano.centraldecontrole.resource;

import br.com.lcano.centraldecontrole.dto.CategoriaDTO;
import br.com.lcano.centraldecontrole.service.CategoriaDespesaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class CategoriaDespesaResourceTest {
    @Mock
    private CategoriaDespesaService categoriaDespesaService;

    @InjectMocks
    private CategoriaDespesaResource categoriaDespesaResource;

    @BeforeEach
    void setUp() {
        categoriaDespesaService = mock(CategoriaDespesaService.class);
        categoriaDespesaResource = new CategoriaDespesaResource(categoriaDespesaService);
    }

    @Test
    void testGetTodasCategoriasDespesa() {
        CategoriaDTO categoriaDTO1 = new CategoriaDTO();
        categoriaDTO1.setId(1L);
        categoriaDTO1.setDescricao("Transporte");

        CategoriaDTO categoriaDTO2 = new CategoriaDTO();
        categoriaDTO2.setId(1L);
        categoriaDTO2.setDescricao("Alimentação");

        List<CategoriaDTO> categoriasDespesaMock = new ArrayList<>();
        categoriasDespesaMock.add(categoriaDTO1);
        categoriasDespesaMock.add(categoriaDTO2);

        when(categoriaDespesaService.getTodasCategoriasDespesa()).thenReturn(categoriasDespesaMock);

        ResponseEntity<List<CategoriaDTO>> response = categoriaDespesaResource.getTodasCategoriasDespesa();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(categoriasDespesaMock, response.getBody());
        verify(categoriaDespesaService, times(1)).getTodasCategoriasDespesa();
    }
}

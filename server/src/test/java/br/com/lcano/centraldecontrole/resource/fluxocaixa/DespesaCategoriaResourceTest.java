package br.com.lcano.centraldecontrole.resource.fluxocaixa;

import br.com.lcano.centraldecontrole.dto.CategoriaDTO;
import br.com.lcano.centraldecontrole.service.fluxocaixa.DespesaCategoriaService;
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

class DespesaCategoriaResourceTest {
    @Mock
    private DespesaCategoriaService despesaCategoriaService;

    @InjectMocks
    private DespesaCategoriaResource despesaCategoriaResource;

    @BeforeEach
    void setUp() {
        despesaCategoriaService = mock(DespesaCategoriaService.class);
        despesaCategoriaResource = new DespesaCategoriaResource(despesaCategoriaService);
    }

    @Test
    void testGetTodasCategoriasDespesa() {
        CategoriaDTO categoriaDTO1 = new CategoriaDTO();
        categoriaDTO1.setId(1L);
        categoriaDTO1.setDescricao("Transporte");

        CategoriaDTO categoriaDTO2 = new CategoriaDTO();
        categoriaDTO2.setId(2L);
        categoriaDTO2.setDescricao("Alimentação");

        List<CategoriaDTO> categoriasDespesaMock = new ArrayList<>();
        categoriasDespesaMock.add(categoriaDTO1);
        categoriasDespesaMock.add(categoriaDTO2);

        when(despesaCategoriaService.getTodasCategoriasDespesa()).thenReturn(categoriasDespesaMock);

        ResponseEntity<List<CategoriaDTO>> response = despesaCategoriaResource.getTodasCategoriasDespesa();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(categoriasDespesaMock, response.getBody());
        verify(despesaCategoriaService, times(1)).getTodasCategoriasDespesa();
    }
}

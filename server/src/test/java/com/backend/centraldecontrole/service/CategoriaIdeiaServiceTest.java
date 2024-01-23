package com.backend.centraldecontrole.service;

import com.backend.centraldecontrole.dto.CategoriaIdeiaResponseDTO;
import com.backend.centraldecontrole.model.CategoriaIdeia;
import com.backend.centraldecontrole.repository.CategoriaIdeiaRepository;
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
        CategoriaIdeia categoria1 = new CategoriaIdeia(1L, "Categoria1");
        CategoriaIdeia categoria2 = new CategoriaIdeia(2L, "Categoria2");

        List<CategoriaIdeia> listaCategoriaIdeia = Arrays.asList(categoria1, categoria2);

        when(categoriaIdeiaRepository.findAll()).thenReturn(listaCategoriaIdeia);

        List<CategoriaIdeiaResponseDTO> resultado = categoriaIdeiaService.getTodasCategoriasIdeia();

        assertNotNull(resultado);
        assertEquals(2, resultado.size());

        CategoriaIdeiaResponseDTO CategoriaIdeiaResponseDTO1 = resultado.get(0);
        assertEquals(categoria1.getId(), CategoriaIdeiaResponseDTO1.id());
        assertEquals(categoria1.getDescricao(), CategoriaIdeiaResponseDTO1.descricao());

        CategoriaIdeiaResponseDTO CategoriaIdeiaResponseDTO2 = resultado.get(1);
        assertEquals(categoria2.getId(), CategoriaIdeiaResponseDTO2.id());
        assertEquals(categoria2.getDescricao(), CategoriaIdeiaResponseDTO2.descricao());
    }
}

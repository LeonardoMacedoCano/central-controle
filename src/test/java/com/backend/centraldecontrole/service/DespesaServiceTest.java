package com.backend.centraldecontrole.service;

import com.backend.centraldecontrole.dto.DespesaRequestDTO;
import com.backend.centraldecontrole.dto.DespesaResponseDTO;
import com.backend.centraldecontrole.model.CategoriaDespesa;
import com.backend.centraldecontrole.model.Despesa;
import com.backend.centraldecontrole.model.Usuario;
import com.backend.centraldecontrole.repository.CategoriaDespesaRepository;
import com.backend.centraldecontrole.repository.DespesaRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Date;
import java.util.Optional;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@SpringBootTest
class DespesaServiceTest {
    @Mock
    private DespesaRepository despesaRepository;

    @Mock
    private CategoriaDespesaRepository categoriaDespesaRepository;

    @InjectMocks
    private DespesaService despesaService;

    @Test
    void adicionarDespesa_CategoriaEncontrada_RetornaOk() {
        DespesaRequestDTO requestDTO = new DespesaRequestDTO(1L, "Descrição da despesa", 100.0, new Date());
        Usuario usuario = new Usuario();

        CategoriaDespesa categoriaDespesa = new CategoriaDespesa();
        when(categoriaDespesaRepository.findById(1L)).thenReturn(Optional.of(categoriaDespesa));

        ResponseEntity<String> response = despesaService.adicionarDespesa(requestDTO, usuario);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Despesa adicionada com sucesso!", response.getBody());

        verify(despesaRepository, times(1)).save(any());
    }

    @Test
    void adicionarDespesa_CategoriaNaoEncontrada_RetornaBadRequest() {
        DespesaRequestDTO requestDTO = new DespesaRequestDTO(1L, "Descrição da despesa", 100.0, new Date());
        Usuario usuario = new Usuario();

        when(categoriaDespesaRepository.findById(1L)).thenReturn(Optional.empty());

        ResponseEntity<String> response = despesaService.adicionarDespesa(requestDTO, usuario);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Categoria de despesa não encontrada com o id 1", response.getBody());

        verify(despesaRepository, never()).save(any());
    }

    @Test
    public void testListarDespesasDoUsuario() {
        Usuario usuario = new Usuario("usuario teste", "senha123", new Date());

        CategoriaDespesa categoriaDespesa = new CategoriaDespesa("Categoria teste");

        Despesa despesa1 = new Despesa(usuario, categoriaDespesa, "despesa 1", 1.00, new Date());
        Despesa despesa2 = new Despesa(usuario, categoriaDespesa, "despesa 2", 2.00, new Date());
        List<Despesa> listaDespesas = Arrays.asList(despesa1, despesa2);

        when(despesaRepository.findByUsuarioId(usuario.getId())).thenReturn(listaDespesas);

        List<DespesaResponseDTO> resultado = despesaService.listarDespesasDoUsuario(usuario.getId());

        assertNotNull(resultado);
        assertEquals(2, resultado.size());

        DespesaResponseDTO despesaResponseDTO1 = resultado.get(0);
        assertEquals(despesa1.getId(), despesaResponseDTO1.id());
        assertEquals(despesa1.getDescricao(), despesaResponseDTO1.descricao());

        DespesaResponseDTO despesaResponseDTO2 = resultado.get(1);
        assertEquals(despesa2.getId(), despesaResponseDTO2.id());
        assertEquals(despesa2.getDescricao(), despesaResponseDTO2.descricao());
    }
}

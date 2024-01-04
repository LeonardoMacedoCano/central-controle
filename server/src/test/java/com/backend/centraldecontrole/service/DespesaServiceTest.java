package com.backend.centraldecontrole.service;

import com.backend.centraldecontrole.dto.DespesaRequestDTO;
import com.backend.centraldecontrole.dto.DespesaResponseDTO;
import com.backend.centraldecontrole.model.CategoriaDespesa;
import com.backend.centraldecontrole.model.Despesa;
import com.backend.centraldecontrole.model.Usuario;
import com.backend.centraldecontrole.repository.CategoriaDespesaRepository;
import com.backend.centraldecontrole.repository.DespesaRepository;
import com.backend.centraldecontrole.util.MensagemConstantes;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
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
        assertEquals(MensagemConstantes.DESPESA_ADICIONADA_COM_SUCESSO, response.getBody());

        verify(despesaRepository, times(1)).save(any());
    }

    @Test
    void adicionarDespesa_CategoriaNaoEncontrada_RetornaBadRequest() {
        Long IdDespesa = 1L;
        DespesaRequestDTO requestDTO = new DespesaRequestDTO(IdDespesa, "Descrição da despesa", 100.0, new Date());
        Usuario usuario = new Usuario();

        when(categoriaDespesaRepository.findById(1L)).thenReturn(Optional.empty());

        ResponseEntity<String> response = despesaService.adicionarDespesa(requestDTO, usuario);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(MensagemConstantes.CATEGORIA_DESPESA_NAO_ENCONTRADA_COM_ID + IdDespesa, response.getBody());

        verify(despesaRepository, never()).save(any());
    }

    @Test
    public void testListarDespesasDoUsuario() {
        Usuario usuario = new Usuario("usuario teste", "senha123", new Date());
        CategoriaDespesa categoriaDespesa = new CategoriaDespesa("Categoria teste");

        Despesa despesa1 = new Despesa(usuario, categoriaDespesa, "despesa 1", 1.00, new Date());
        Despesa despesa2 = new Despesa(usuario, categoriaDespesa, "despesa 2", 2.00, new Date());

        int anoFiltro = 2024;
        int mesFiltro = 1;

        List<Despesa> listaDespesas = Arrays.asList(despesa1, despesa2);

        when(despesaRepository.findByUsuarioIdAndDataBetween(eq(usuario.getId()), any(Date.class), any(Date.class)))
                .thenReturn(listaDespesas);

        List<DespesaResponseDTO> resultado = despesaService.listarDespesasDoUsuario(usuario.getId(), anoFiltro, mesFiltro);

        assertNotNull(resultado);
        assertEquals(2, resultado.size());

        DespesaResponseDTO despesaResponseDTO1 = resultado.get(0);
        assertEquals(despesa1.getId(), despesaResponseDTO1.id());
        assertEquals(despesa1.getDescricao(), despesaResponseDTO1.descricao());

        DespesaResponseDTO despesaResponseDTO2 = resultado.get(1);
        assertEquals(despesa2.getId(), despesaResponseDTO2.id());
        assertEquals(despesa2.getDescricao(), despesaResponseDTO2.descricao());
    }


    @Test
    public void testEditarDespesa() {
        Long idDespesa = 1L;
        DespesaRequestDTO despesaRequestDTO = new DespesaRequestDTO(idDespesa, "teste", 10.00, new Date());
        Usuario usuario = new Usuario("username", "senha", new Date());

        CategoriaDespesa categoria = new CategoriaDespesa("categoria teste");
        when(categoriaDespesaRepository.findById(eq(despesaRequestDTO.idCategoria()))).thenReturn(Optional.of(categoria));

        Despesa despesaExistente = new Despesa(usuario, categoria, "teste", 10.00, new Date());
        when(despesaRepository.findById(idDespesa)).thenReturn(Optional.of(despesaExistente));

        ResponseEntity<String> response = despesaService.editarDespesa(idDespesa, despesaRequestDTO, usuario);

        assertEquals(HttpStatus.OK, response.getStatusCode(), "O status HTTP deve ser OK");
        assertEquals(MensagemConstantes.DESPESA_EDITADA_COM_SUCESSO, response.getBody());

        verify(despesaRepository, times(1)).findById(idDespesa);
        verify(categoriaDespesaRepository, times(1)).findById(eq(despesaRequestDTO.idCategoria()));

        ArgumentCaptor<Despesa> despesaCaptor = ArgumentCaptor.forClass(Despesa.class);
        verify(despesaRepository, times(1)).save(despesaCaptor.capture());

        Despesa despesaEditada = despesaCaptor.getValue();
        assertEquals(categoria, despesaEditada.getCategoria(), "A categoria da despesa deve ser a mesma da categoria mockada");
    }

    @Test
    public void testExcluirDespesa() {
        Long idDespesa = 1L;

        Usuario usuario = new Usuario("username", "senha", new Date());
        CategoriaDespesa categoria = new CategoriaDespesa("categoria teste");

        Despesa despesaExistente = new Despesa(usuario, categoria, "teste", 10.00, new Date());
        when(despesaRepository.findById(idDespesa)).thenReturn(Optional.of(despesaExistente));

        ResponseEntity<String> response = despesaService.excluirDespesa(idDespesa);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(MensagemConstantes.DESPESA_EXCLUIDA_COM_SUCESSO, response.getBody());

        verify(despesaRepository, times(1)).findById(idDespesa);
        verify(despesaRepository, times(1)).delete(despesaExistente);
    }
}

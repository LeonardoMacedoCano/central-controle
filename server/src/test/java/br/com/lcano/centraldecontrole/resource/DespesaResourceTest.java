package br.com.lcano.centraldecontrole.resource;

import br.com.lcano.centraldecontrole.dto.DespesaDTO;
import br.com.lcano.centraldecontrole.domain.Usuario;
import br.com.lcano.centraldecontrole.dto.DespesaResumoMensalDTO;
import br.com.lcano.centraldecontrole.service.DespesaService;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.Collections;
import java.util.Date;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;

class DespesaResourceTest {
    @Mock
    private DespesaService despesaService;

    @InjectMocks
    private DespesaResource despesaResource;

    public DespesaResourceTest() {
        MockitoAnnotations.openMocks(this);
        despesaResource = new DespesaResource(despesaService);
    }

    @Test
    void testGerarDespesa() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        DespesaDTO despesaDTO = new DespesaDTO();

        Usuario usuario = new Usuario(1L, "teste", "senha123", new Date(), true);

        when(request.getAttribute("usuario")).thenReturn(usuario);

        ResponseEntity<Object> response = despesaResource.gerarDespesa(despesaDTO, request);

        verify(despesaService, times(1)).gerarDespesa(despesaDTO, usuario);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testEditarDespesa() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        DespesaDTO despesaDTO = new DespesaDTO();
        despesaDTO.setId(1L);

        Usuario usuario = new Usuario(1L, "teste", "senha123", new Date(), true);

        when(request.getAttribute("usuario")).thenReturn(usuario);

        ResponseEntity<Object> response = despesaResource.editarDespesa(despesaDTO.getId(), despesaDTO, request);

        verify(despesaService, times(1)).editarDespesa(despesaDTO.getId(), despesaDTO, usuario);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testExcluirDespesa() {
        HttpServletRequest request = mock(HttpServletRequest.class);

        Usuario usuario = new Usuario(1L, "teste", "senha123", new Date(), true);

        when(request.getAttribute("usuario")).thenReturn(usuario);
        Long idDespesa = 1L;

        ResponseEntity<Object> response = despesaResource.excluirDespesa(idDespesa);
        verify(despesaService, times(1)).excluirDespesa(idDespesa);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testListarDespesaResumoMensalDTO() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        Pageable pageable = mock(Pageable.class);

        Integer ano = 2024;
        Integer mes = 3;

        Usuario usuario = new Usuario(1L, "teste", "senha123", new Date(), true);
        Page<DespesaResumoMensalDTO> despesasDTO = new PageImpl<>(Collections.emptyList());

        when(request.getAttribute("usuario")).thenReturn(usuario);
        when(despesaService.listarDespesaResumoMensalDTO(usuario.getId(), ano, mes, pageable)).thenReturn(despesasDTO);

        ResponseEntity<Page<DespesaResumoMensalDTO>> response = despesaResource.listarDespesaResumoMensalDTO(request, ano, mes, pageable);

        assertIterableEquals(despesasDTO.getContent(), Objects.requireNonNull(response.getBody()).getContent());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}

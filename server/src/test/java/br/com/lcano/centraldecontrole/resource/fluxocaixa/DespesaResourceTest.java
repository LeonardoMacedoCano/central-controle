package br.com.lcano.centraldecontrole.resource.fluxocaixa;

import br.com.lcano.centraldecontrole.domain.Usuario;
import br.com.lcano.centraldecontrole.domain.fluxocaixa.Despesa;
import br.com.lcano.centraldecontrole.domain.fluxocaixa.DespesaCategoria;
import br.com.lcano.centraldecontrole.domain.fluxocaixa.DespesaFormaPagamento;
import br.com.lcano.centraldecontrole.domain.fluxocaixa.DespesaParcela;
import br.com.lcano.centraldecontrole.dto.fluxocaixa.DespesaDTO;
import br.com.lcano.centraldecontrole.service.fluxocaixa.DespesaService;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class DespesaResourceTest {
    @Mock
    private DespesaService despesaService;

    @InjectMocks
    private DespesaResource despesaResource;

    public DespesaResourceTest() {
        MockitoAnnotations.openMocks(this);
        despesaResource = new DespesaResource(despesaService);
    }

    /*
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
    */

    @Test
    void testGetDespesaByIdWithParcelas() {
        HttpServletRequest request = mock(HttpServletRequest.class);

        DespesaCategoria categoria = new DespesaCategoria();
        categoria.setId(1L);
        categoria.setDescricao("Teste");

        DespesaFormaPagamento formaPagamento = new DespesaFormaPagamento();
        formaPagamento.setId(1L);
        formaPagamento.setDescricao("Cartao");

        Long idDespesa = 1L;
        Despesa despesa = new Despesa();
        despesa.setId(idDespesa);
        despesa.setCategoria(categoria);

        List<DespesaParcela> parcelas = Arrays.asList(
                new DespesaParcela(1L, 1, new Date(), 5.00, false, despesa, formaPagamento),
                new DespesaParcela(2L, 2, new Date(), 10.00, false, despesa, formaPagamento),
                new DespesaParcela(3L, 3, new Date(), 15.00, false, despesa, formaPagamento));

        despesa.setParcelas(parcelas);

        Usuario usuario = new Usuario(1L, "teste", "senha123", new Date(), true);

        when(request.getAttribute("usuario")).thenReturn(usuario);
        when(despesaService.getDespesaByIdWithParcelas(idDespesa)).thenReturn(despesa);

        ResponseEntity<DespesaDTO> response = despesaResource.getDespesaByIdWithParcelas(idDespesa);

        verify(despesaService, times(1)).getDespesaByIdWithParcelas(idDespesa);
        assertEquals(parcelas.size(), Objects.requireNonNull(response.getBody()).getParcelasDTO().size());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(idDespesa, Objects.requireNonNull(response.getBody()).getId());
    }
}

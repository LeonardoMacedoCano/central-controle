package br.com.lcano.centraldecontrole.resource.fluxocaixa;

import br.com.lcano.centraldecontrole.dto.fluxocaixa.DespesaFormaPagamentoDTO;
import br.com.lcano.centraldecontrole.service.fluxocaixa.DespesaFormaPagamentoService;
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
import static org.mockito.Mockito.times;

public class DespesaFormaPagamentoResourceTest {
    @Mock
    private DespesaFormaPagamentoService despesaFormaPagamentoService;

    @InjectMocks
    private DespesaFormaPagamentoResource despesaFormaPagamentoResource;

    @BeforeEach
    void setUp() {
        despesaFormaPagamentoService = mock(DespesaFormaPagamentoService.class);
        despesaFormaPagamentoResource = new DespesaFormaPagamentoResource(despesaFormaPagamentoService);
    }

    @Test
    void testGetTodasFormaPagamento() {
        DespesaFormaPagamentoDTO formaPagamentoDTO1 = new DespesaFormaPagamentoDTO();
        formaPagamentoDTO1.setId(1L);
        formaPagamentoDTO1.setDescricao("Cartão de crédito");

        DespesaFormaPagamentoDTO formaPagamentoDTO2 = new DespesaFormaPagamentoDTO();
        formaPagamentoDTO2.setId(2L);
        formaPagamentoDTO2.setDescricao("Pix");

        List<DespesaFormaPagamentoDTO> formaPagamentoDTOS = new ArrayList<>();
        formaPagamentoDTOS.add(formaPagamentoDTO1);
        formaPagamentoDTOS.add(formaPagamentoDTO2);

        when(despesaFormaPagamentoService.getTodasFormaPagamento()).thenReturn(formaPagamentoDTOS);

        ResponseEntity<List<DespesaFormaPagamentoDTO>> response = despesaFormaPagamentoResource.getTodasFormaPagamento();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(formaPagamentoDTOS, response.getBody());
        verify(despesaFormaPagamentoService, times(1)).getTodasFormaPagamento();
    }
}

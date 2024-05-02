package br.com.lcano.centraldecontrole.resource;

import br.com.lcano.centraldecontrole.dto.FormaPagamentoDTO;
import br.com.lcano.centraldecontrole.service.FormaPagamentoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@SpringBootTest
public class FormaPagamentoResourceTest {
    @Mock
    private FormaPagamentoService formaPagamentoService;

    @InjectMocks
    private FormaPagamentoResource formaPagamentoResource;

    @BeforeEach
    void setUp() {
        formaPagamentoService = mock(FormaPagamentoService.class);
        formaPagamentoResource = new FormaPagamentoResource(formaPagamentoService);
    }

    @Test
    void testGetTodasFormaPagamento() {
        FormaPagamentoDTO formaPagamentoDTO1 = new FormaPagamentoDTO();
        formaPagamentoDTO1.setId(1L);
        formaPagamentoDTO1.setDescricao("Cartão de crédito");

        FormaPagamentoDTO formaPagamentoDTO2 = new FormaPagamentoDTO();
        formaPagamentoDTO2.setId(2L);
        formaPagamentoDTO2.setDescricao("Pix");

        List<FormaPagamentoDTO> formaPagamentoDTOS = new ArrayList<>();
        formaPagamentoDTOS.add(formaPagamentoDTO1);
        formaPagamentoDTOS.add(formaPagamentoDTO2);

        when(formaPagamentoService.getTodasFormaPagamento()).thenReturn(formaPagamentoDTOS);

        ResponseEntity<List<FormaPagamentoDTO>> response = formaPagamentoResource.getTodasFormaPagamento();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(formaPagamentoDTOS, response.getBody());
        verify(formaPagamentoService, times(1)).getTodasFormaPagamento();
    }
}

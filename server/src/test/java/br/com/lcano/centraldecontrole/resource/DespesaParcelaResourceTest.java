package br.com.lcano.centraldecontrole.resource;

import br.com.lcano.centraldecontrole.service.DespesaParcelaService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class DespesaParcelaResourceTest {
    @Mock
    private DespesaParcelaService despesaParcelaService;

    @InjectMocks
    private DespesaParcelaResource despesaParcelaResource;

    public DespesaParcelaResourceTest() {
        MockitoAnnotations.openMocks(this);
        despesaParcelaResource = new DespesaParcelaResource(despesaParcelaService);
    }

    @Test
    void testGetValorTotalParcelasPorMes() {
        int ano = 2024;
        int mes = 3;
        double valorTotalEsperado = 500.0;

        when(despesaParcelaService.calcularValorTotalParcelasMensal(ano, mes)).thenReturn(valorTotalEsperado);

        ResponseEntity<Double> responseEntity = despesaParcelaResource.getValorTotalParcelasPorMes(mes, ano);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(valorTotalEsperado, responseEntity.getBody());
    }
}

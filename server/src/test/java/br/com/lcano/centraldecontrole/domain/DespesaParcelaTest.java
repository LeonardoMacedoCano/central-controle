package br.com.lcano.centraldecontrole.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

public class DespesaParcelaTest {
    private DespesaParcela despesaParcela;

    @BeforeEach
    void setUp() {
        despesaParcela = new DespesaParcela();
    }

    @Test
    void testCriarDespesaParcela() {
        assertNotNull(despesaParcela);
    }

    @Test
    void testGettersAndSetters() {
        despesaParcela.setId(1L);
        despesaParcela.setNumero(1);
        Date dataVencimento = new Date();
        despesaParcela.setDataVencimento(dataVencimento);
        despesaParcela.setValor(100.00);
        despesaParcela.setPago(true);

        assertEquals(1L, despesaParcela.getId());
        assertEquals(1, despesaParcela.getNumero());
        assertEquals(dataVencimento, despesaParcela.getDataVencimento());
        assertEquals(100.00, despesaParcela.getValor());
        assertTrue(despesaParcela.isPago());
    }

    @Test
    void testEqualsAndHashCode() {
        DespesaParcela despesaParcela1 = new DespesaParcela();
        DespesaParcela despesaParcela2 = new DespesaParcela();

        assertEquals(despesaParcela1, despesaParcela2);
        assertEquals(despesaParcela1.hashCode(), despesaParcela2.hashCode());
    }

    @Test
    void testToString() {
        despesaParcela.setId(1L);
        assertEquals("DespesaParcela(id=1)", despesaParcela.toString());
    }
}
